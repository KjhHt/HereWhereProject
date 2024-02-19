from flask import Flask
from flask_restful import Api
from flask_cors import CORS
from api.google_hotel_crawling_api import Hotel_Crawling
from api.google_hotel_api import Hotel
from api.google_restaurant_api import Restaurant
from api.google_attraction_api import Attraction
from api.SearchImage import SearchImage
from api.booking_api import Booking
from api.youtube_api import Youtube

app = Flask(__name__)
api = Api(app)
CORS(app)

api.add_resource(SearchImage,'/searchImage')
api.add_resource(Hotel,'/hotel')
api.add_resource(Hotel_Crawling,'/hotel_crawling')
api.add_resource(Restaurant,'/restaurant')
api.add_resource(Attraction,'/attraction')
api.add_resource(Booking,'/booking')
api.add_resource(Youtube,'/youtube')


if __name__ == '__main__':
    app.run(debug=True)