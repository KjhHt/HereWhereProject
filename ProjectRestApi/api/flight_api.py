from flask_restful import Resource
from model.amadeus_flight import search_flight_cost

class Booking(Resource):
    def get(self):
        pass