from flask import Flask, request
from flask_restful import Api, Resource
from PIL import Image
import base64
import io
from ultralytics import YOLO
import os
import uuid

class ImageDetection(Resource):
    def __init__(self):
        # YOLO 모델 로드
        self.model = YOLO('../model/best.pt')

    def post(self):

        data = request.get_json()
        base64_str = data.get('image')
        base64_str = base64_str.split(',')[-1]  # 데이터 URI 스키마 제거
        # Base64 문자열을 이미지로 디코딩
        img_bytes = base64.b64decode(base64_str)
        img_io = io.BytesIO(img_bytes)
        img = Image.open(img_io)

        # 이미지 이름 생성 (임시 파일로 저장)
        img_name = f'temp_{uuid.uuid4()}.jpg'
        img.save(img_name)

        # 모델에 이미지를 입력하여 예측 수행
        results = self.model.predict([img_name], save=True, line_width=1, conf=0.5)
        result = results[0]  # 첫 번째 결과만 사용

        # 결과에서 클래스 인덱스와 확신도 가져오기
        class_indices = result.boxes.cls
        confidences = result.boxes.conf
        class_confidences = list(zip(class_indices.tolist(), confidences.tolist()))

        # 확신도가 0.9 이상인 레이블만 선택
        selected_labels = [result.names[int(idx)] for idx, conf in class_confidences if conf >= 0.9]
        selected_labels = list(set(selected_labels))  # 중복 제거

        # 임시 파일 삭제
        os.remove(img_name)

        return {'labels': selected_labels}