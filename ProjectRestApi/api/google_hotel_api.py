from flask_restful import Resource,reqparse
from model.google_hotel import find_nearby_places

class Hotel(Resource):
    def get(self):
        api_key = 'AIzaSyBMpSPfY-brXtLzGQNDvnsJyf-r61u-H6k'
        parser=reqparse.RequestParser()
        parser.add_argument('lat',location='args', type=float, required=True, help="Latitude is required.")
        parser.add_argument('lng',location='args', type=float, required=True, help="Longitude is required.")
        args=parser.parse_args()
        location = f'{args["lat"]},{args["lng"]}'  # 위례 경도
        info = find_nearby_places(api_key, location, radius=3000, keyword='hotel', max_results=15)

        return info