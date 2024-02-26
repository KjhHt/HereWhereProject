import requests
from bs4 import BeautifulSoup
from flask_restful import Resource

class Overseas(Resource):
    def get(self):
        overseas_trip = self.get_overseas_trip()
        return overseas_trip

    def get_overseas_trip(self):
        url = 'https://www.ybtour.co.kr/'
        response = requests.get(url)
        soup = BeautifulSoup(response.text, 'html.parser')
        text = [element.get_text(strip=True) for element in soup.find_all('em', class_='txt_local')][:10]
        thumb_spans = soup.find_all('span', class_='thumb')
        img_urls = ['https:' + thumb_span.find('img')['src'] for thumb_span in thumb_spans][:10]
        overseas_trip = []
        for t, i in zip(text, img_urls):
            overseas_trip.append({'location': t, 'image': i})
        return overseas_trip

