from datetime import datetime

def transform_data_for_file(rates_data):
    # 초기 변환된 데이터 구조
    transformed = {"time": [], "rates": {}}
    # 조회된 데이터를 순회하면서 구조 변환
    for item in rates_data:
        transformed["time"].append(item["time"])
        for currency, rate in item["rates"].items():
            if currency not in transformed["rates"]:
                transformed["rates"][currency] = []
            transformed["rates"][currency].append(f'{(1/rate):.2f}')
    return transformed

def transform_data(series):
    # JSON 객체 생성
    json_obj = {
        "COUNTRY_NAME": series["COUNTRY_NAME"],
        "COUNTRY_CODE": series["COUNTRY_CODE"],
        "CURRENCY_NAME": series["CURRENCY_NAME"],
        "CURRENCY_CODE": series["CURRENCY_CODE"],
        "RATES": {
            "date":[],
            "rate":[]
        }
    }
    for date, rate in series[4:].items():
        json_obj["RATES"]['date'].append(date)
        json_obj["RATES"]['rate'].append(rate)
    return json_obj

def convert_date_format(idx):
    try:
        # 유효한 날짜 형식인지 시도
        valid_date = datetime.strptime(idx, "%Y.%m.%d")
        # 유효하다면 변환
        return valid_date.strftime("%y.%m.%d")
    except ValueError:
        # 유효한 날짜 형식이 아니면 원래 문자열 반환
        return idx