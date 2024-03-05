from flask_restful import Resource, reqparse
from model.currency import CurrencySingleton

class CurrencyApi(Resource):
    def __init__(self):
        self.currency=CurrencySingleton.get_instance()
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('currencyCode', type=str, location='args')
    def get(self):
        args = self.reqparse.parse_args()
        currency_code = args.get('currencyCode')

        if currency_code:
            # currencyCode가 제공된 경우, 해당 통화 정보 반환
            currency_data = self.currency.get_currency_list(currency_code)
            if currency_data:
                return currency_data
            else:
                return {'message': 'Currency not found'}, 404