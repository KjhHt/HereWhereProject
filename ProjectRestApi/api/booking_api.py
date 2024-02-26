from flask_restful import Resource, reqparse
from model.booking import hotel_search

class Booking(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        parser.add_argument('lat', location='args')  # 'lat'와 'lng'를 분리
        parser.add_argument('lng', location='args')  # 'lat'와 'lng'를 분리
        parser.add_argument('number', location='args')  # 'lat'와 'lng'를 분리
        parser.add_argument('check_in', location='args')
        parser.add_argument('check_out', location='args')
        args = parser.parse_args()
        lat = args['lat']
        lng = args['lng']
        number = args['number']
        check_in = args['check_in']
        check_out = args['check_out']
        info = hotel_search(lat, lng, number, check_in, check_out)  # lat와 lng를 제대로 전달

        return info