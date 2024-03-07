from flask_restful import Resource, reqparse
from flask import make_response
import base64
from google.cloud import vision
from google.oauth2 import service_account
from google.cloud import translate_v2 as translate
import os
from PIL import Image, ImageDraw, ImageFont
import numpy as np
import io
import cv2

class ImageOcr(Resource):
    def __init__(self):
        self.credentials_path = os.environ['GOOGLE_APPLICATION_CREDENTIALS']
        self.translate_client = translate.Client(credentials=self.authenticate_service_account())

    def authenticate_service_account(self):
        credentials = service_account.Credentials.from_service_account_file(self.credentials_path)
        scoped_credentials = credentials.with_scopes(['https://www.googleapis.com/auth/cloud-platform'])
        return scoped_credentials

    def translate_text(self, text, target='ko'):
        result = self.translate_client.translate(text, target_language=target)
        return result['translatedText']

    def detect_labels(self, base64Encoded):

        credentials = self.authenticate_service_account()
        image_content = base64.b64decode(base64Encoded)
        image = Image.open(io.BytesIO(image_content))
        client = vision.ImageAnnotatorClient(credentials=credentials)
        vision_image = vision.Image(content=image_content)
        response = client.text_detection(image=vision_image)
        texts = response.text_annotations

        # 텍스트를 그릴 준비
        draw = ImageDraw.Draw(image)

        # 폰트 파일의 경로와 크기를 지정
        font = ImageFont.truetype('malgun.ttf', 25)
        # PIL 이미지를 OpenCV 이미지로 변환
        opencv_image = cv2.cvtColor(np.array(image), cv2.COLOR_RGB2BGR)
        for i, text in enumerate(texts):
            if i == 0:
                continue
            vertices = [(v.x, v.y) for v in text.bounding_poly.vertices]
            label = text.description
            translated_label = self.translate_text(label)
            print(translated_label)

            '''
            # 텍스트 있는곳 블러처리하려면 이걸로
            x, y, w, h = vertices[0][0], vertices[0][1], vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][
                1]
            roi = opencv_image[y:y + h, x:x + w]
            blurred_roi = cv2.GaussianBlur(roi, (99, 99), 30)
            opencv_image[y:y + h, x:x + w] = blurred_roi
            '''

            # 텍스트가 그려질 부분을 모자이크 처리.
            x, y, w, h = vertices[0][0], vertices[0][1], vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1]
            roi = opencv_image[y:y + h, x:x + w]

            # 이미지를 축소한 후 다시 확대하여 모자이크 효과
            # 여기서 (10, 10)은 축소할 때의 크기로, 이 값이 작을수록 모자이크의 정도가 높아집니다.
            resized = cv2.resize(roi, (10, 10))

            mosaic_roi = cv2.resize(resized, (w, h), interpolation=cv2.INTER_NEAREST)

            opencv_image[y:y + h, x:x + w] = mosaic_roi

        image = Image.fromarray(cv2.cvtColor(opencv_image, cv2.COLOR_BGR2RGB))

        # PIL 이미지에 텍스트
        draw = ImageDraw.Draw(image)
        font = ImageFont.truetype('malgun.ttf', 25)
        for i, text in enumerate(texts):
            if i == 0:
                continue

            vertices = [(v.x, v.y) for v in text.bounding_poly.vertices]
            label = text.description
            translated_label = self.translate_text(label)
            # 여기에서 단어 바꿀수있음!! 해당 사진에 맞게 바꾸면 될듯
            #print(translated_label)
            if translated_label == '미국 사람':
                translated_label='아메리카노'
            if translated_label == '재스민 향수':
                translated_label='쟈스민'
            if translated_label == '만다린 오렌지':
                translated_label = '귤'
            if translated_label == '만다린 오렌지':
                translated_label = '귤'
            if translated_label == '에게':
                translated_label = '테이크아웃'
            if translated_label == '가다':
                translated_label = ''
            draw.text((vertices[0][0], vertices[0][1] -5 ), translated_label, fill='rgb(255, 255, 0)', font=font)

        image.save('blurred_text_image.jpg')

        # 이미지를 base64로 인코딩하여 반환
        buf = io.BytesIO()
        image.save(buf, format='PNG')
        byte_im = buf.getvalue()
        encoded_string = base64.b64encode(byte_im).decode('utf-8')

        return encoded_string

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('base64Encoded', location='json', required=True)
        args = parser.parse_args()
        if args['base64Encoded'] is None:
            return 'base64Encoded 값이 필요합니다.', 400
        encoded_image = self.detect_labels(args['base64Encoded'])
        return make_response(encoded_image)