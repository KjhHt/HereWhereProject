from flask_restful import Resource
from model.amadeus_flight import AmadeusSingleton
from flask import request
from utils.amadeus_util import get_airports_data,get_airline_codes

class FlightBooking(Resource):
    def __init__(self):
        self.amadeus=AmadeusSingleton.get_instance()
        self.airports_data=get_airports_data()
        self.airline_data=get_airline_codes()
    def get(self):
        path=request.path
        if path == '/airports_list':
            return [self.airports_data,self.airline_data]
        elif path == '/searchFlight':
            # 클라이언트로부터 받은 검색 조건
            origin = request.args.get('origin')
            destination = request.args.get('destination')
            departureDate = request.args.get('departureDate')
            # 필요한 경우 추가 검색 조건을 여기에 추가

            # 검색 조건을 기반으로 항공편 검색 수행
            search_results = self.amadeus.search_flights_list({
                'origin': origin,
                'destination': destination,
                'departureDate': departureDate,
                # 추가 검색 조건
            })

            if search_results:
                return search_results
            else:
                return {'message': 'No flights found or error in search'}, 404
        else:
            return {'error': 'Invalid path'}, 404
