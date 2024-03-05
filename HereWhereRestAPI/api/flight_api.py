from flask_restful import Resource
from model.naver_flight_crawling import NaverFlightSingleton

class Flight(Resource):
    def __init__(self):
        self.flight=NaverFlightSingleton.get_instance()

    def get(self):
        return self.flight.get_flight_data()