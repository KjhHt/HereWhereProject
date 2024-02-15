import time,requests
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import  By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import os,json,re

def chrome_driver():
    driver_path = f'{os.path.join(os.path.dirname(__file__),"chromedriver")}'
    service = Service(executable_path=driver_path)
    options = webdriver.ChromeOptions()
    options.add_experimental_option("detach", True)
    options.add_argument('headless')
    options.add_argument('--disable-gpu')
    options.add_argument('window-size=1920x1080')
    options.add_argument('User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36')

    return webdriver.Chrome(service=service, options=options)


def hotel_address(hotel_name,place):
    endpoint = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"
    params = {
        'input': f'{hotel_name} {place}',
        'inputtype': 'textquery',
        'fields': 'formatted_address,geometry',
        'key': 'AIzaSyBMpSPfY-brXtLzGQNDvnsJyf-r61u-H6k',
    }
    response = requests.get(endpoint, params=params)
    result = response.json()
    if 'candidates' in result and len(result['candidates']) > 0:
        candidate = result['candidates'][0]
        formatted_address = candidate['formatted_address']
        location = candidate['geometry']['location']
        latitude, longitude = location['lat'], location['lng']
        return formatted_address, latitude, longitude
    else:
        return None, None, None  # 반환값을 수정하여 None을 반환하도록 변경


def google_hotel(place):
    try:
        driver = chrome_driver()
        # 웹 페이지 열기
        driver.get(f'https://www.google.co.kr/travel/search?q={place}')
        '''
        number = driver.find_element(By.CSS_SELECTOR, "#yDmH0d > c-wiz.zQTmif.SSPGKf.AglWE > div > c-wiz > div.dTQsx.gBIxsf > div.MawWP > div.wMMMNd > c-wiz > div > div > div.E4DaWc.AJr5uf > div > div.tbDMNe > div.YFfNHd > div > div > div > div.cQnuXe.k0gFV > div > div")
        number.click()

        btn1 = driver.find_element(By.CSS_SELECTOR, 'button[jsname="DUGJie"]')
        btn2 = driver.find_element(By.CSS_SELECTOR, 'button[jsname="TdyTDe"]')

        if person == 1:
            btn1.click()
        elif person == 2:
            pass
        elif person == 3:
            btn2.click()
        elif person == 4:
            for _ in range(2):
                btn2.click()
        elif person == 5:
            for _ in range(3):
                btn2.click()
        else:
            for _ in range(4):
                btn2.click()

        button_element = driver.find_element(By.CSS_SELECTOR, 'button[jsname="kZlJze"]')
        button_element.click()

        # 체크인
        start = driver.find_element(By.CSS_SELECTOR, "#yDmH0d > c-wiz.zQTmif.SSPGKf.AglWE > div > c-wiz > div.dTQsx.gBIxsf > div.MawWP > div.wMMMNd > c-wiz > div > div > div.E4DaWc.AJr5uf > div > div.tbDMNe > div.khj5De.Yxswxc > div > div > div.Ryi7tc.hh3Grb > div.oSuIZ.YICvqf.i9kmrf.cd29Sd > div > input")
        driver.execute_script("arguments[0].value = '';", start)
        start.send_keys(f'{check_in}')
        start.send_keys(Keys.TAB)
        time.sleep(0.8)

        # 체크아웃
        end = driver.find_element(By.CSS_SELECTOR, "#yDmH0d > c-wiz.zQTmif.SSPGKf.AglWE > div > c-wiz > div.dTQsx.gBIxsf > div.MawWP > div.wMMMNd > c-wiz > div > div > div.E4DaWc.AJr5uf > div > div.tbDMNe > div.khj5De.Yxswxc > div > div > div.Ryi7tc.hh3Grb > div:nth-child(2) > div > input")
        driver.execute_script("arguments[0].value = '';", end)
        # 값 입력
        end.send_keys(f'{check_out}')
        end.send_keys(Keys.ENTER)
        time.sleep(3)
        '''

        stores = []

        WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR, '#id > c-wiz > c-wiz:nth-child(4) > div > div > div > div.kCsInf.ZJqrAd.qiy8jf > div > div.jVsyI > div.BqzcJ > div > h2')))
        soup = BeautifulSoup(driver.page_source, 'html.parser')


        # 업소명
        name = [element.get_text(strip=True) for element in soup.find_all('h2', class_='BgYkof ogfYpf ykx2he')][:15]

        # 가격
        prices = [element.get_text(strip=True) for element in soup.select('#id > c-wiz > c-wiz > div > div > div > div.kCsInf.ZJqrAd.qiy8jf > div > div.jVsyI > div.PwV1Ac.BIjKEe')][:15]

        # 1박 가격
        pattern_1 = re.compile(r'₩[\d,]+')
        price = []
        for p in prices:
            match = re.search(pattern_1, p)
            if match:
                price.append(match.group(0))
            else:
                price.append('')
        '''
        # 총 가격
        pattern_2 = re.compile(r'₩([\d,]+)[1-9]*박')
        total_price = []

        for p in prices:
            match = re.search(pattern_2, p)
            if match:
                price_value = match.group(1)
                total_price.append(f'₩{price_value[:-1]}')
            else:
                total_price.append('')
        '''

        # 편의시설
        convenient = [element.get_text(strip=True) for element in soup.find_all('div', class_='RJM8Kc')][:15]

        # 별점
        star = [element.get_text(strip=True) for element in soup.find_all('span', class_='KFi5wf lA0BZ')][:15]

        # 이미지
        img_urls = [element['src'] for element in soup.select('#id > c-wiz > c-wiz > div > div > div > div.EyfHd.LBZpid.RF6XVd.MCh51b.KlnVZd.Pvavaf.hWbOBd.pO9Eic.lRagtb > div.Un8Fvc.g0zIfc.Aoa2Me.wVXXIc.KeXIs.SbzNmc > div.tP1BTe > div > div > div:nth-child(1) > img')][:15]



        for n, s, p, c, i in zip(name, star, price, convenient, img_urls):
            # 호텔명으로부터 주소와 위도, 경도 가져오기
            address, latitude, longitude = hotel_address(n,place)

            if address is not None:
                stores.append({
                    'name': n,
                    'rating': s,
                    'price': p,

                    'convenience': c,
                    'image': i,

                    'address': address,
                    'latlng': {'lat':latitude,'lng':longitude}
                })
            else:
                print(f"Skipping {n} due to missing information.")

        for store in stores:
            print(store)

        with open('google_hotel.json', 'w', encoding='utf8') as f:
            f.write(json.dumps(stores, indent=4, ensure_ascii=False))

        return stores


    except TimeoutException as e:
        print('지정한 요소를 찾을 수 없어요:', e)


