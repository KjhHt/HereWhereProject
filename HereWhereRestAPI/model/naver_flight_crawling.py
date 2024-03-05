from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import os
import pandas as pd
from datetime import datetime, timedelta
import threading

class NaverFlight:
    def __init__(self):
        filename = os.path.join('data', 'airports_dataset.csv')
        self.df=pd.read_csv(filename)
        self.continents=self.df['CONTINENT'].unique()
        self.flights = {continent: [] for continent in self.continents}
        self.cached_flights=None
        self.last_update_time=None
        self.is_updating = False  # 데이터 수집 중인지 여부를 나타내는 플래그
        self.start_periodic_update()

    def should_update_data(self):
        # 마지막 업데이트 시간이 설정되지 않았거나, 마지막 업데이트로부터 5분 이상 경과한 경우 True 반환
        if self.last_update_time is None or (datetime.now() - self.last_update_time) > timedelta(minutes=5):
            return True
        return False
    def chrome_driver(self):
        driver_path = f'{os.path.join(os.path.dirname(__file__),"chromedriver.exe")}'
        service = Service(executable_path=driver_path)
        options = webdriver.ChromeOptions()
        options.add_experimental_option("detach", True)
        options.add_argument('headless')
        options.add_argument('--disable-gpu')
        options.add_argument('window-size=1920x1080')
        options.add_argument('User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36')
        return webdriver.Chrome(service=service, options=options)

    def convert_data(self,element):
        soup = BeautifulSoup(element, 'html.parser')
        # 정보 추출
        img_src = soup.find('img', class_='list_img__KlPzV').get('src').replace('f70_70','f328_216')
        departure_name = soup.find(class_='start').find(class_='list_name__3lU-C').text
        departure_code = soup.find(class_='start').find(class_='list_code__1l3Oi').text
        destination_name = soup.find(class_='list_end__393_f').find(class_='list_name__3lU-C').text
        destination_code = soup.find(class_='list_end__393_f').find(class_='list_code__1l3Oi').text
        info = soup.find(class_='list_info__-GgqG').text.split(', ')
        oneway = info[0] if len(info) > 1 else "직항"
        duration = info[1] if len(info) > 1 else info[0]
        price = int(soup.find(class_='amount').text.replace(',', ''))

        continent_series = self.df[self.df['IATA'] == destination_code][['CONTINENT','LAT','LNG']]
        continent = continent_series['CONTINENT'].values[0] if not continent_series.empty else '없음'
        latlng=continent_series[['LAT','LNG']].values[0].tolist()

        if len(self.flights[continent]) < 8:
            data_dict = {
                'continent': continent,
                'departure': departure_name,
                'departureCode': departure_code,
                'destination': destination_name,
                'destinationCode': destination_code,
                'oneway': oneway,
                'duration': duration,
                'price': price,
                'img': img_src,
                'latlng': latlng
            }
            self.flights[continent].append(data_dict)

    def get_flight_data(self):
        if self.cached_flights:
            return self.cached_flights
        else:
            self.cached_flights

    def naver_flight(self):
        if self.is_updating:  # 이미 업데이트 중이면 실행하지 않음
            return
        driver = self.chrome_driver()
        self.is_updating = True  # 업데이트 시작 플래그 설정
        # 데이터 업데이트가 필요한 경우, flights 딕셔너리를 재설정합니다.
        self.flights = {continent: [] for continent in self.continents}
        try:
            # 웹 페이지 열기
            driver.get(f'https://flight.naver.com/flights/everywhere/monthly/SEL')
            WebDriverWait(driver, 5).until(
                EC.visibility_of_element_located((By.XPATH, '//*[@id="__next"]/div/div/main/article/div[2]/div/div[2]/div[1]/button[1]')))
            elements = driver.find_elements(By.XPATH, '//*[@id="__next"]/div/div/main/article/div[2]/div/div[2]/div/button[1]')
            for element in elements:
                self.convert_data(element.get_attribute('outerHTML'))
            self.cached_flights = self.flights
            self.last_update_time = datetime.now()
        except TimeoutException as e:
            print('지정한 요소를 찾을 수 없어요:', e)
        finally:
            self.is_updating=False
            driver.close()
    def start_periodic_update(self):
        if self.should_update_data():
            self.naver_flight()
        # 다음 실행을 위한 타이머를 설정합니다.
        threading.Timer(300, self.start_periodic_update).start()  # 300초 == 5분

class NaverFlightSingleton:
    _instance = None
    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = NaverFlight()
        return cls._instance