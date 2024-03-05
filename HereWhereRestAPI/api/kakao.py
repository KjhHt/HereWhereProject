from flask import request
from flask_restful import Resource
import requests


api_key = '9bf6e699ee1c1e0dbb5f2836ea79d37a'  # 발급받은 API KEY

class Kakao(Resource):
    def addr_to_lat_lon(self,addr):
        url = 'https://dapi.kakao.com/v2/local/search/address.json?query={address}'.format(address=addr)
        headers = {"Authorization": "KakaoAK " + api_key}
        response = requests.get(url, headers=headers)

        if response.status_code == 200:
            result = response.json()
            if 'documents' in result and result['documents']:
                match_first = result['documents'][0]['address']
                x = float(match_first['x'])
                y = float(match_first['y'])
                return x, y

        return None

    def post(self):
        data = request.get_json()

        if not data or 'address' not in data:
            return {'error': 'POST 요청 본문에 주소가 제공되지 않았습니다.'}, 400

        address = data['address']
        result = self.addr_to_lat_lon(address)

        if result:
            return {'x': result[0], 'y': result[1]}
        else:
            return {'error': '유효하지 않은 주소 또는 좌표를 찾을 수 없습니다.'}, 404

