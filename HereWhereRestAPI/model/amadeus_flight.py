from amadeus import Client, Location
from amadeus.client.errors import ResponseError,ClientError
from utils.amadeus_util import get_airports_data
from concurrent.futures import ThreadPoolExecutor, as_completed
import random,time,os
from dotenv import load_dotenv

class Amadeus():
    def __init__(self):
        load_dotenv()
        self.client_id = os.getenv('AMADEUS_CLIENT_ID')
        self.client_secret = os.getenv('AMADEUS_CLIENT_SECRET')
        self.amadeus = Client(
            client_id=self.client_id,
            client_secret=self.client_secret
        )
        self.airport_data = get_airports_data()

    def get_nearest_airport(self, lat, lng):
        try:
            response = self.amadeus.reference_data.locations.airports.get(
                latitude=lat,
                longitude=lng
            )
            print(response)
            nearest_airports = response.data
            if nearest_airports:
                # 가장 가까운 공항의 IATA 코드를 반환
                return nearest_airports[0]['iataCode']
            else:
                return None
        except ResponseError as error:
            print('error:', error)
            return None

    def process_flight_offer_data(self,data):
        """
        API 응답 데이터에서 항공권 예약에 필요한 정보를 추출하고 정리하는 함수

        Parameters:
            data (dict): API 응답 데이터

        Returns:
            list: 처리된 항공편 정보 리스트
        """
        processed_data = []
        for offer in data['data']:
            flight_offer = {
                'offerId': offer['id'],  # 항공편 오퍼 ID
                'source': offer['source'],  # 데이터 소스 (GDS 등)
                'ticketingRequired': offer['instantTicketingRequired'],  # 즉시 발권 필요 여부
                'nonHomogeneous': offer['nonHomogeneous'],  # 동질적이지 않은지 여부
                'oneWay': offer['oneWay'],  # 편도 여부
                'lastTicketingDate': offer['lastTicketingDate'],  # 마지막 발권 날짜
                'bookableSeats': offer['numberOfBookableSeats'],  # 예약 가능한 좌석 수
                'totalPrice': offer['price']['total'],  # 총 가격
                'currency': offer['price']['currency'],  # 통화
                'itineraries': []  # 여정 정보
            }

            for itinerary in offer['itineraries']:
                for segment in itinerary['segments']:
                    itinerary_info = {
                        'departureAirport': segment['departure']['iataCode'],  # 출발 공항 코드
                        'arrivalAirport': segment['arrival']['iataCode'],  # 도착 공항 코드
                        'departureTime': segment['departure']['at'],  # 출발 시간
                        'arrivalTime': segment['arrival']['at'],  # 도착 시간
                        'carrierCode': segment['carrierCode'],  # 운항 항공사 코드
                        'flightNumber': segment['number'],  # 비행기 번호
                        'aircraftCode': segment['aircraft']['code'],  # 항공기 모델 코드
                        'duration': segment['duration'],  # 비행 시간
                        'stops': segment['numberOfStops'],  # 경유지 수
                        'blacklistedInEU': segment['blacklistedInEU']  # EU 블랙리스트 여부
                    }
                    flight_offer['itineraries'].append(itinerary_info)

            processed_data.append(flight_offer)

        return processed_data


    def search_flights_post(self, origin_destinations):
        search_params = {
            "currencyCode": "KRW",
            "originDestinations": origin_destinations,
            "travelers": [{"id": "1", "travelerType": "ADULT", "fareOptions": ["STANDARD"]}],
            "sources": ["GDS"],
            "searchCriteria": {"maxFlightOffers": 1}
        }
        api_call = lambda: self.amadeus.shopping.flight_offers_search.post(body=search_params)
        response = self.make_request_with_exponential_backoff(api_call)
        if response and response.result:
            print(response.result)
            return response.result
        else:
            print(f"Error in API response: {response}")
            return None


    def search_flight(self, airport_data):
        try:
            '''
            type: 데이터 타입을 나타냅니다. 여기서는 'flight-offer'로, 항공편 오퍼 정보임을 의미합니다.
            id: 항공편 오퍼의 고유 식별자입니다.
            source: 데이터 소스를 나타냅니다. 'GDS'는 Global Distribution System의 약자로, 항공 예약 시스템을 의미합니다.
            instantTicketingRequired: 즉시 발권이 필요한지 여부를 나타냅니다. False인 경우 즉시 발권이 필요하지 않음을 의미합니다.
            nonHomogeneous: 항공편이 동질적이지 않은지 여부를 나타냅니다. 동일 항공사의 항공편만 포함되어 있는지의 여부를 의미할 수 있습니다.
            oneWay: 편도 여부를 나타냅니다.
            lastTicketingDate와 lastTicketingDateTime: 항공권을 예약할 수 있는 마지막 날짜와 시간입니다.
            numberOfBookableSeats: 예약 가능한 좌석 수입니다.
            itineraries: 여정 정보를 담고 있는 배열입니다. 각 여정은 하나 이상의 구간(segment)으로 구성됩니다.
            price: 항공편의 가격 정보를 나타냅니다. 여기에는 총 가격, 기본 가격, 수수료, 최종 가격이 포함됩니다.
            pricingOptions: 가격 책정 옵션을 나타냅니다.
            validatingAirlineCodes: 항공편을 검증하는 항공사 코드입니다.
            travelerPricings: 여행자 별 가격 정보를 나타냅니다.
            '''

            response = self.make_request_with_exponential_backoff(
                lambda: self.amadeus.shopping.flight_offers_search.get(
                    originLocationCode=self.client_data['origin'],
                    destinationLocationCode=airport_data['IATA'],
                    departureDate=self.client_data['departure_date'],
                    adults=self.client_data['adults'],
                    currencyCode='KRW'
                )
            )

            if response and response.data:
                flight = response.data[0]
                return {
                    'destination': airport_data['detail'],
                    'price': int(flight['price']['total'])
                }
        except ResponseError as error:
            print(error)
            return None

    #실제 요청 함수
    def search_flights_list(self, client_data):
        results = []

        with ThreadPoolExecutor(max_workers=10) as executor:
            futures = []

            # 클라이언트로부터 받은 검색 조건을 기반으로 검색 파라미터 구성
            origin_destination = {
                "id": "1",
                "originLocationCode": client_data['origin'],
                "destinationLocationCode": client_data['destination'],
                "departureDateTimeRange": {
                    "date": client_data['departureDate'],
                }
            }

            # 검색 작업을 ThreadPoolExecutor로 비동기적으로 수행
            future = executor.submit(self.search_flights_post, [origin_destination])
            futures.append(future)

            # 모든 futures가 완료될 때까지 대기
            for future in as_completed(futures):
                try:
                    response = future.result()
                    if response and 'data' in response:
                        try:
                            processed_result = self.process_flight_offer_data(response)
                            results.extend(processed_result)  # 결과 리스트에 추가
                        except KeyError as e:
                            print(f"Missing key {e} in response")
                    else:
                        print(f"Error in processing response: No data found")
                except Exception as exc:
                    print(f"Exception while processing response: {exc}")

        print('results:', results)
        return results

    def airport_city_search(self, query):
        """
        공항 및 도시 검색 (자동 완성)
        :param query: 사용자가 입력한 검색 쿼리 (도시 이름, 공항 이름 등)
        """
        try:
            # Airport & City Search (autocomplete) API 호출
            response = self.amadeus.reference_data.locations.get(
                keyword=query,
                subType=Location.ANY  # 'AIRPORT', 'CITY' 등을 포함
            )
            return response.data
        except ResponseError as error:
            print(f"Error: {error}")
            return None
    def make_request_with_exponential_backoff(self, api_call):
        max_attempts = 5
        attempt = 0
        while attempt < max_attempts:
            try:
                response = api_call()
                if response:
                    return response
                else:
                    raise Exception("Empty response")
            except ResponseError as e:
                # ResponseError가 발생하면 status_code 사용
                print(f"ResponseError with status code {e.status_code}: {e}")
                if e.status_code == 429:
                    sleep_time = (2 ** attempt) + random.uniform(0.5, 1.0)
                    print(f"Rate limit exceeded. Retrying in {sleep_time:.2f} seconds.")
                    time.sleep(sleep_time)
                else:
                    return {'error': str(e)}
            except ClientError as e:
                # ClientError 발생 시 status_code가 없으므로 str(e) 사용
                print(f"ClientError: {e}")
                return {'error': str(e)}
            except Exception as e:
                print(f"An unexpected error occurred: {e}")
                if attempt >= max_attempts - 1:
                    return {'error': str(e)}
                sleep_time = (2 ** attempt) + random.uniform(0.5, 1.0)
                print(f"Unexpected error. Retrying in {sleep_time:.2f} seconds.")
                time.sleep(sleep_time)
            attempt += 1
        return {'error': "API request failed after maximum number of retries"}

    def flight_price_analysis(self):
        self.amadeus.analytics.itinerary_price_metrics()

class AmadeusSingleton:
    _instance = None

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = Amadeus()
        return cls._instance