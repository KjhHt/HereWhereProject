from flask_restful import Resource,reqparse
from model.google_attraction import find_nearby_places

class Attraction(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        parser.add_argument('lat', location='args')  # 'lat'와 'lng'를 분리
        parser.add_argument('lng', location='args')  # 'lat'와 'lng'를 분리
        args = parser.parse_args()
        api_key = 'AIzaSyBMpSPfY-brXtLzGQNDvnsJyf-r61u-H6k'
        lat = args['lat']
        lng = args['lng']
        max_results = 25
        info = find_nearby_places(api_key, lat, lng, max_results)  # lat와 lng를 제대로 전달

        return info