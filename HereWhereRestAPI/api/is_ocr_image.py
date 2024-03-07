from flask_restful import Resource, reqparse
from google.oauth2 import service_account
from google.cloud import vision
import base64
import os

class isOcrImage(Resource):
    def __init__(self):
        self.credentials_path = os.environ['GOOGLE_APPLICATION_CREDENTIALS']

    def authenticate_service_account(self):
        credentials = service_account.Credentials.from_service_account_file(self.credentials_path)
        scoped_credentials = credentials.with_scopes(['https://www.googleapis.com/auth/cloud-platform'])
        return scoped_credentials

    def detect_labels(self, base64Encoded):
        credentials = self.authenticate_service_account()
        base64Encoded = base64Encoded.split(",")[1]  # 데이터 URI 스키마 제거
        padding_needed = len(base64Encoded) % 4
        if padding_needed:
            base64Encoded += '=' * (4 - padding_needed)
        image_content = base64.b64decode(base64Encoded)
        client = vision.ImageAnnotatorClient(credentials=credentials)
        vision_image = vision.Image(content=image_content)
        response = client.text_detection(image=vision_image)
        texts = response.text_annotations
        return len(texts) > 0

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('base64Encoded', location='json', required=True, action='append')
        args = parser.parse_args()
        if args['base64Encoded'] is None:
            return 'base64Encoded 값이 필요합니다.', 400
        results = [self.detect_labels(image) for image in args['base64Encoded']]
        return results