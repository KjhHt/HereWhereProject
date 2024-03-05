import csv,os,json,datetime,PyPDF2
from forex_python.converter import CurrencyRates
import pandas as pd
from google.cloud import translate

# Initialize Translation client
c = CurrencyRates()

def translate_text(
    text: str,  # 번역하려는 텍스트
    project_id: str = "627426374935-0jconei7d80bnf1finfrc3saeqmrt56b.apps.googleusercontent.com",  # 여기에 Google Cloud 프로젝트 ID를 입력하세요
    target_language_code: str = "ko",  # 목표 언어 코드
    source_language_code: str = "en-US"  # 원본 언어 코드
) -> translate.TranslationServiceClient:
    """Translating Text."""



    client = translate.TranslationServiceClient()

    location = "global"
    parent = f"projects/{project_id}/locations/{location}"

    response = client.translate_text(
        request={
            "parent": parent,
            "contents": [text],
            "mime_type": "text/plain",
            "source_language_code": source_language_code,
            "target_language_code": target_language_code,
        }
    )

    # Display the translation for each input text provided
    for translation in response.translations:
        print(f"Translated text: {translation.translated_text}")

    return response

def clean_airlines_data(airlines_df):
    # 데이터프레임 복사본 생성
    df = airlines_df.copy()
    # 'Operational Status'가 'Y'인 항공사만 필터링
    df = df[df['Operational Status'] == 'Y']
    # 결측치가 있는 행 제거
    df.dropna(subset=['IATA'], inplace=True)
    # 중복 데이터 제거 (IATA 코드 기준)
    # df.drop_duplicates(subset=['IATA'], inplace=True)
    return df

def clean_airports_data(airports_df):
    # 데이터프레임 복사본 생성
    df = airports_df.copy()
    # 결측치가 있는 행 제거
    df.dropna(subset=['IATA'], inplace=True)
    # 중복 데이터 제거 (IATA 코드 기준)
    # df.drop_duplicates(subset=['IATA'], inplace=True)
    return df

def get_airports_data():
    # 항공사 데이터 로드
    airlines_filename = os.path.join('data','archive','Final_airlines')
    airlines_df = pd.read_csv(airlines_filename, sep=',',
                              names=['Airline ID', 'Name_airline', 'Alias', 'ICAO', 'IATA', 'Callsign',
                                     'Country/Region', 'Operational Status'], header=None, on_bad_lines='skip')


    # 공항 데이터 로드
    airports_filename = os.path.join('data','archive','airports_mod.dat')
    airports_df = pd.read_csv(airports_filename, sep=',',
                              names=['Airport ID', 'Name_airport', 'City', 'Country', 'IATA', 'ICAO', 'Latitude',
                                     'Longitude', 'Altitude', 'Timezone', 'DST', 'Tz database time zone'], header=None,
                              on_bad_lines='skip')
    airlines_df=clean_airlines_data(airlines_df)
    airports_df=clean_airports_data(airports_df)
    # IATA 코드를 기준으로 항공사 데이터에서 Name과 Operational Status 정보를 공항 데이터에 매핑
    merged_df = pd.merge(airports_df, airlines_df[['IATA', 'Name_airline', 'Operational Status']],
                         on='IATA',how='left',suffixes=('_airport', '_airline'))

    return merged_df


def get_airline_codes():
    df = pd.read_csv('./data/carrier_dataset.csv', encoding='utf-8')
    airline_codes = dict(zip(df['carrierCode'], df['carrierName']))
    return airline_codes

def get_airline_codes_csv():
    df = pd.read_csv('./data/airline_carriercode.csv', encoding='utf-8', header=None)
    airline_codes = dict(zip(df[0], df[1]))
    return airline_codes

def convert_to_krw(usd_amount):

    usd_to_krw_rate = c.get_rate('USD', 'KRW')  # USD에서 KRW로의 환율
    krw_amount= usd_amount * usd_to_krw_rate
    return round(krw_amount)

def save_response_to_file(data, filename):
    """
    응답 데이터를 JSON 파일로 저장합니다.

    :param data: 저장할 데이터
    :param filename: 생성될 JSON 파일의 이름
    """
    with open(filename, 'w', encoding='utf-8') as file:
        json.dump(data, file, ensure_ascii=False, indent=4)
        print(f"Data saved to {filename}")

# 날짜 및 시간 변환 함수
def format_datetime(dt_str):
    dt = datetime.datetime.strptime(dt_str, "%Y-%m-%dT%H:%M:%S")
    return dt.strftime("%Y년 %m월 %d일 %H시 %M분")

# 비행 시간 변환 함수
def format_duration(duration):
    hours, minutes = duration.split('H')
    if "M" in minutes:
        minutes = minutes.rstrip('M')
    else:
        minutes = "0"
    return f"{hours}시간 {minutes}분"

# 항공편 정보 변환 함수
def convert_flight_data(flight_data):
    airline_codes=get_airline_codes()
    converted_data = {
        "예약 ID": flight_data["id"],
        "데이터 소스": flight_data["source"],
        "즉시 티켓 발권 필요": "필요함" if flight_data["instantTicketingRequired"] else "필요하지 않음",
        "동질적 항공편 여부": "동질적임" if flight_data["nonHomogeneous"] else "동질적이지 않음",
        "편도 여부": "편도" if flight_data["oneWay"] else "왕복",
        "최종 티켓팅 날짜": format_datetime(flight_data["lastTicketingDate"] + "T00:00:00"),
        "예약 가능 좌석 수": flight_data["numberOfBookableSeats"],
        "가격": f'{convert_to_krw(float(flight_data["price"]))} 원',
        "여정": []
    }

    for itinerary in flight_data["itineraries"]:
        converted_data["여정"].append({
            "출발지": itinerary["departure"],
            "도착지": itinerary["arrival"],
            "출발 시간": format_datetime(itinerary["departureTime"]),
            "도착 시간": format_datetime(itinerary["arrivalTime"]),
            "항공사": airline_codes.get(itinerary["carrierCode"], "알 수 없음"),
            "비행기 번호": itinerary["flightNumber"],
            "항공기 모델": itinerary["aircraftCode"],
            "비행 시간": format_duration(itinerary["duration"])
        })
    return converted_data


def get_airports_data():
    filename = os.path.join('data', 'airports_dataset.csv')
    df = pd.read_csv(filename, encoding='utf-8')
    # DataFrame을 직접 리스트의 딕셔너리로 변환
    data = df[['IATA', 'COUNTRY', 'CITY', 'AIRPORT_NAME']].to_dict(orient='records')
    return data  # 이제 data는 JSON 객체 형태인, 즉 Python 리스트의 딕셔너리입니다.

def convert_df(row):
    return {
        'iata': row['IATA'],
        'country': row['COUNTRY'],
        'city': row['CITY'],
        'airport_name': row['AIRPORT_NAME']
    }


# Function to extract airlines and codes
def extract_airlines_and_codes(pdf_path):
    # Open the PDF file
    with open(pdf_path, 'rb') as file:
        reader = PyPDF2.PdfReader(file)
        airlines_dict = {}

        # Iterate through each page
        for page in reader.pages:
            # Extract text from the page
            text = page.extract_text()
            if text:
                # Split text into lines
                lines = text.split('\n')
                # Iterate through each line
                for line in lines:
                    # Check if line contains an airline code
                    if line.strip() and len(line.split()) >= 2 and line.split()[-1].isupper() and len(
                            line.split()[-1]) == 2:
                        # Extract airline name and code
                        airline = ' '.join(line.split()[:-1])
                        code = line.split()[-1]
                        # Add to dictionary
                        airlines_dict[code] = airline
    return airlines_dict

def lookup_airport_info(iata, df_airports):
    # Attempt to get the row that matches the IATA code
    if iata in df_airports['IATA'].values:
        airport_info = df_airports[df_airports['IATA'] == iata].iloc[0]
        return {
            'continent': airport_info['CONTINENT'],
            'country': airport_info['COUNTRY'],
            'city': airport_info['CITY'],
            'airport_name': airport_info['AIRPORT_NAME']
        }
    else:
        # Return None or default values if not found
        return {
            'continent': None,
            'country': None,
            'city': None,
            'airport_name': None
        }