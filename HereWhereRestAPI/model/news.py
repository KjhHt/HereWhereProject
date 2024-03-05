import requests
from bs4 import BeautifulSoup
from geopy.geocoders import Nominatim
from googletrans import Translator


# pip install geopy
# pip install lxml

def translate_to_korean(text):
    translator = Translator()
    translation = translator.translate(text, dest='ko_KR')
    return translation.text


def get_city_name(lat, lng):
    geolocator = Nominatim(user_agent="my_geocoding_app", timeout=10)
    location = geolocator.reverse((lat, lng), exactly_one=True, language="ko")

    # 위치 정보 객체에서 국가명과 도시 이름 추출
    country_name = location.raw['address'].get('country', '')
    country_name_korean = translate_to_korean(country_name)

    if country_name == '대한민국':
        city_name = location.raw['address'].get('city', '')
        if city_name:
            city_name_korean = translate_to_korean(city_name)
            location_korean = f"{country_name_korean} {city_name_korean}"
        else:
            # 대한민국의 도시 이름이 없는 경우에도 국가 이름만 반환
            location_korean = country_name_korean
    else:
        # 국가가 대한민국이 아닌 경우에는 국가 이름만 반환
        location_korean = country_name_korean

    return location_korean

def get_news_from_location(lat, lng):
    location_name = get_city_name(lat, lng)
    news = []
    if location_name:
        api_url = "https://news.google.com/rss/search?q="
        search_query = location_name.replace(' ', '+') + "+여행"
        search_url = api_url + search_query
        response = requests.get(search_url)
        if response.status_code == 200:
            soup = BeautifulSoup(response.content, 'xml')
            items = soup.find_all('item')
            for item in items[:5]:
                news.append({
                    'title' : item.title.text,
                    'link' : item.link.text
                })
        else:
            print("Failed to fetch news.")

        return news
    else:
        print("Failed to get location name.")

