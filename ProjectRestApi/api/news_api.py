from flask_restful import Resource, reqparse
from model.news import get_news_from_location

class News(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        parser.add_argument('lat', location='args')
        parser.add_argument('lng', location='args')
        args = parser.parse_args()
        lat = args['lat']
        lng = args['lng']
        info = get_news_from_location(lat, lng)
        return info