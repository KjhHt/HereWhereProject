import requests
import json

def find_nearby_places(api_key, lat, lng, max_results, radius=3000, keyword='restaurant'):
    api_url = f'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={lat},{lng}&radius={radius}&keyword={keyword}&key={api_key}&language=ko'
    try:
        response = requests.get(api_url)
        data = response.json()
        # places = sorted(data.get('results', []), key=lambda x: x.get('rating', 0), reverse=True)[:max_results]
        places = data.get('results', [])
        places_info = []
        for place in places:
            place_id = place['place_id']
            name = place['name']
            place_details = get_place_details_and_photo(api_key, place_id)
            places_info.append({
                'restaurant': name,
                'address': place_details['formatted_address'],
                'rating': place.get('rating', 3.0),
                'lat': place['geometry']['location']['lat'],
                'lng': place['geometry']['location']['lng'],
                'operating_time': place_details['opening_hours'],
                'tel': place_details['phone_number'],
                'image': place_details['photo_url']
            })

        with open('restaurant.json', 'w', encoding='utf-8') as f:
            json.dump(places_info, f, ensure_ascii=False, indent=4)

        for i in places_info:
            print(i)


        return places_info

    except Exception as e:
        print(f'에러: {e}')

def get_place_details_and_photo(api_key, place_id):
    details_url = f'https://maps.googleapis.com/maps/api/place/details/json?key={api_key}&place_id={place_id}&fields=formatted_address,formatted_phone_number,opening_hours,photos&language=ko'
    try:
        response = requests.get(details_url)
        data = response.json()

        result = data.get('result', {})
        formatted_address = result.get('formatted_address', '자세한 주소를 제공하지 않는 음식점입니다')
        phone_number = result.get('formatted_phone_number', '전화번호를 제공하지 않는 음식점입니다')

        opening_hours = result.get('opening_hours', {}).get('weekday_text', '운영 시간을 제공하지 않는 음식점입니다')
        if isinstance(opening_hours, list):  # Opening hours 정보를 딕셔너리로 변환합니다.
            weekday_hours = {}
            for day in opening_hours:
                day_name, hours = day.split(': ', 1)
                weekday_hours[day_name] = hours
            opening_hours = weekday_hours

        photo_reference = result.get('photos', [])
        if photo_reference:
            photo_reference = photo_reference[0]['photo_reference']
            photo_url = f'https://maps.googleapis.com/maps/api/place/photo?key={api_key}&photoreference={photo_reference}&maxwidth=400'
        else:
            photo_url = '사진을 제공하지 않는 음식점 입니다'

        return {
            'formatted_address': formatted_address,
            'phone_number': phone_number,
            'opening_hours': opening_hours,
            'photo_url': photo_url,
        }
    except Exception as e:
        print(f'에러: {e}')
        return {
            'formatted_address': '자세한 주소를 제공하지 않는 음식점입니다',
            'phone_number': '전화번호를 제공하지 않는 음식점입니다',
            'opening_hours': '운영 시간을 제공하지 않는 음식점입니다',
            'photo_url': '사진을 제공하지 않는 음식점 입니다',
        }
