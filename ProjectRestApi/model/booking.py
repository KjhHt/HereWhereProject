import json,requests,os
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
from selenium.common.exceptions import TimeoutException


#환율 함수
def get_exchange_rate(currency):
    try:
        driver_path = f'{os.path.join(os.path.dirname(__file__), "chromedriver.exe")}'
        service = Service(executable_path=driver_path)
        options = webdriver.ChromeOptions()
        options.add_experimental_option("detach", True)
        options.add_argument('headless')
        options.add_argument('--disable-gpu')
        options.add_argument('window-size=1920x1080')
        options.add_argument('User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36')
        driver = webdriver.Chrome(service=service, options=options)

        url = f'https://www.google.co.kr/search?q={currency}+환율'
        driver.get(url)
        WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR,
                                                                       '#knowledge-currency__updatable-data-column > div.b1hJbf > div.dDoNo.ikb4Bb.gsrt > span.DFlfde.SwHCTb')))
        soup = BeautifulSoup(driver.page_source, 'html.parser')

        for element in soup.find_all('span', class_='DFlfde SwHCTb'):
            text = element.get_text(strip=True)
            text_without_comma = text.replace(',', '')
            try:
                value = float(text_without_comma)
                return value
            except ValueError:
                print(f"문자열을 부동 소수점으로 변환할 수 없습니다: {text}")

        return None

    except Exception as e:
        print(f"오류가 발생했습니다: {e}")


def hotel_search(lat, lng, number, check_in, check_out):
    try:
        url = "https://booking-com.p.rapidapi.com/v1/hotels/search-by-coordinates"

        querystring = {
            "locale": "ko",
            "room_number": "1",
            "checkin_date": f'{check_in}',
            "checkout_date": f'{check_out}',
            "filter_by_currency": "KRW",
            "latitude": str(lat),
            "longitude": str(lng),
            "adults_number": str(number),
            "order_by": "distance",
            "units": "metric",
            "page_number": "0",
            "include_adjacency": "true",
            "categories_filter_ids": "class::2,class::4,free_cancellation::1"
        }

        headers = {
            "X-RapidAPI-Key": os.getenv("X-RapidAPI-Key"),
            "X-RapidAPI-Host": "booking-com.p.rapidapi.com"
        }

        response = requests.get(url, headers=headers, params=querystring)
        print('결과',response.json())
        data = response.json()['result']
        hotels = []

        currency = None
        exchange_rate = None

        for result in data:
            if currency is None:
                currency = result.get('price_breakdown').get('currency')

                if currency == 'KRW':
                    exchange_rate = 1
                else:
                    exchange_rate = get_exchange_rate(currency)

            name = result['hotel_name_trans']
            review_score = result['review_score'] if result['review_score'] is not None else 5.0
            lat = result['latitude']
            lng = result['longitude']
            address = result['address_trans']
            price = result['min_total_price']
            image = result['max_photo_url']

            if exchange_rate is not None:
                hotels.append({
                    'hotel': name,
                    'address': address,
                    'lat': lat,
                    'lng': lng,
                    'review': review_score,  # 리뷰를 따로 튜플로 저장하지 않음
                    'price': "{:,.0f}원".format(price * exchange_rate),
                    'img_urls': image
                })
        hotels.sort(key=lambda x: x['review'], reverse=True)
        # 정렬된 호텔 데이터 출력
        for hotel in hotels:
            pass

        # JSON 파일로 저장
        with open('booking.json', 'w', encoding='utf8') as f:
            f.write(json.dumps(hotels, indent=4, ensure_ascii=False))
        return hotels

    except TimeoutException as e:
        print('지정한 요소를 찾을 수 없어요:', e)