from flask_restful import Resource,reqparse
from model.google_hotel_crawling import google_hotel

class Hotel_Crawling(Resource):
    def get(self):
        parser=reqparse.RequestParser()
        parser.add_argument('address',location='args')
        args=parser.parse_args()
        place = args['address']  # input('검색할 장소를 입력해주세요')
        info = google_hotel(place)

        return info