import requests

def get_weather_info(lat, lng, api_key):
    url = f'http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lng}&appid={api_key}&lang=kr'
    response = requests.get(url)
    data = response.json()
    print(data)

    weather_info = {
        'weather': data['weather'][0]['main'],
        'country': data['name'],
        'weather_ko': data['weather'][0]['description'],
        'temp': f"{round(data['main']['temp'] - 273.15, 1)} ℃",
        'temp_min': f"{round(data['main']['temp_min'] - 273.15, 1)} ℃",
        'temp_max': f"{round(data['main']['temp_max'] - 273.15, 1)} ℃",
        'humidity': f"{data['main']['humidity']} %",
        'wind': f"{data['wind']['speed']} m/s"
    }
    return weather_info