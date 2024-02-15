from flask_restful import Resource, reqparse
from model.booking import hotel_search

class Booking(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        parser.add_argument('lat', location='args')  # 'lat'와 'lng'를 분리
        parser.add_argument('lng', location='args')  # 'lat'와 'lng'를 분리
        args = parser.parse_args()
        number = 2
        check_in = "2024-04-01"
        check_out = "2024-04-03"
        lat = args['lat']
        lng = args['lng']
        info = hotel_search(lat, lng, number, check_in, check_out)  # lat와 lng를 제대로 전달

        return info


