import os
import requests
import pandas as pd
from datetime import datetime
from dotenv import load_dotenv
from utils.currency_utils import transform_data


class Currency:
    def __init__(self):
        load_dotenv()
        self.filename = os.path.join('data', 'currency_dataset_date.csv')
        self.currency_df = pd.read_csv(self.filename, encoding='utf-8-sig')
        self.url = "https://currency-exchange.p.rapidapi.com/exchange"
        self.headers = {
            "X-RapidAPI-Key": os.getenv('RAPID_API_KEY'),
            "X-RapidAPI-Host": os.getenv('CURRENT_CURRENCY_RAPID_API_HOST')
        }
        self.get_currency_list('USD')

    def get_currency_list(self, currency_code):
        today_str = datetime.now().strftime("%Y.%m.%d")
        if not self.check_rate_cached(currency_code, today_str):
            today_rate = self.get_current_currency_to_krw(currency_code)
            if today_rate is not None:
                self.update_currency_rate(currency_code, today_rate, today_str)
            else:
                print(f"Unable to fetch the rate for {currency_code} on {today_str}")
                return {}  # 빈 딕셔너리 반환하여 None 반환 방지

        currency_series = self.currency_df.loc[self.currency_df['CURRENCY_CODE'] == currency_code].iloc[0]
        transformed_data = transform_data(currency_series)
        return transformed_data

    def update_currency_rate(self, currency_code, rate, date_str):
        if date_str not in self.currency_df.columns:
            self.currency_df[date_str] = pd.NA
        self.currency_df.loc[self.currency_df['CURRENCY_CODE'] == currency_code, date_str] = rate
        self.currency_df.to_csv(self.filename, index=False, encoding='utf-8-sig')

    def get_current_currency_to_krw(self, currency_code):
        querystring = {"from": currency_code, "to": "KRW", "q": "1.0"}
        try:
            response = requests.get(self.url, headers=self.headers, params=querystring)
            response.raise_for_status()  # 오류 상태 코드가 응답된 경우 예외 발생
            return response.json()
        except Exception as e:
            print(f"Error fetching currency data for {currency_code}: {e}")
            return None

    def check_rate_cached(self, currency_code, date_str):
        if date_str not in self.currency_df.columns or currency_code not in self.currency_df['CURRENCY_CODE'].values:
            return False
        return not pd.isnull(self.currency_df.loc[self.currency_df['CURRENCY_CODE'] == currency_code, date_str].iloc[0])

class CurrencySingleton:
    _instance = None

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = Currency()
        return cls._instance