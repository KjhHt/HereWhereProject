from flask_restful import Resource, reqparse
from model.weather import get_weather_info

class Weather(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        parser.add_argument('lat', location='args')
        parser.add_argument('lng', location='args')
        args = parser.parse_args()
        api_key = 'a3860bbe1b7c1eb05625e5ae026b4787'
        lat = args['lat']
        lng = args['lng']
        info = get_weather_info(lat, lng, api_key)
        return info
