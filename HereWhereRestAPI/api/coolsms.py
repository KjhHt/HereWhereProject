from flask import Flask, request
from flask_restful import Api, Resource
from sdk.api.message import Message
from sdk.exceptions import CoolsmsException
import random

app = Flask(__name__)
api = Api(app)

class Coolsms(Resource):
    def send_sms(self, to):
        api_key = "NCSIILOS0D8OGV0U"
        api_secret = "HTAPKAQZHWNKF7GY17SUKEFPJFCTPOFA"
        random_number = str(random.randint(100000, 999999))

        params = {
            'type': 'sms',
            'to': to,
            'from': '01022025637',
            'text': f'WhereHere 인증코드 번호: [{random_number}]입니다.'
        }

        cool = Message(api_key, api_secret)

        try:
            response = cool.send(params)
            return {
                "success_count": response['success_count'],
                "error_count": response['error_count'],
                "group_id": response['group_id'],
                "error_list": response.get("error_list", []),
                "random_number":random_number
            }

        except CoolsmsException as e:
            return {
                "error_code": e.code,
                "error_message": e.msg
            }

    def post(self):
        # POST 요청에서 전화번호를 가져옵니다.
        data = request.get_json()
        to = data.get('to', '')  # 전화번호는 JSON 데이터에서 'to' 키로 전달받습니다.

        if not to:
            return {"error": "전화번호를 입력하세요."}, 400

        response = self.send_sms(to)

        return response

