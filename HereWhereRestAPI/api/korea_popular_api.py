import requests
from bs4 import BeautifulSoup
from flask_restful import Resource

class Korea(Resource):
    def get(self):
        korea_trip = self.get_trip()
        return korea_trip

    def get_trip(self):
        url = 'https://kr.trip.com/travel-guide/south-korea-100042/cities?locale=ko-KR&curr=KRW'
        response = requests.get(url)
        soup = BeautifulSoup(response.text, 'html.parser')
        text = [element.get_text(strip=True) for element in soup.find_all('div', class_='AdaptiveLayout-sc-1uaehnx-0 gOKERC')][:10]
        img_urls = [element['src'] for element in soup.find_all('img', class_='img')][:10]
        trip = []
        for t, i in zip(text, img_urls):
            trip.append({'location': t, 'image': i })
        return trip



