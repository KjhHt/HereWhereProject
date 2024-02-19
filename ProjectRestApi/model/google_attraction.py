import requests,json,os
from dotenv import load_dotenv

def find_nearby_places(api_key, lat, lng, max_results,radius=4000):
    keywords_str = '|'.join(['landmark', 'museum', 'park'])
    api_url = f'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={lat},{lng}&radius={radius}&keyword={keywords_str}&key={api_key}&language=ko'
    try:
        response = requests.get(api_url)
        data = response.json()
        places = sorted(data.get('results', []), key=lambda x: x.get('rating', 0), reverse=True)[:max_results]

        with open('attraction.json', 'w', encoding='utf8') as f:
            places_info = []
            for place in places:
                place_info = print_place_info(api_key, place)
                places_info.extend(place_info)
            f.write(json.dumps(places_info, indent=4, ensure_ascii=False))

        for i in places_info:
            print(i)

        return places_info

    except Exception as e:
        print(f'에러: {e}')

def print_place_info(api_key,place):
    info = []
    name = place['name']
    vicinity = place.get('vicinity', 'N/A')
    rating = place.get('rating', 'N/A')
    latitude = place['geometry']['location']['lat']
    longitude = place['geometry']['location']['lng']
    place_id = place['place_id']
    open = get_place_details(api_key, place_id)

    photos = place.get('photos', [])
    if photos:
        photo_reference = photos[0].get('photo_reference', '')
        image = get_photo_url(photo_reference)
    else:
        image = '-'

    info.append({'attraction': name, 'address': vicinity, 'rating': rating, 'lat': latitude, 'lng': longitude,
                 'operating_time': open, 'image': image})

    return info

def get_place_details(api_key, place_id):
    details_url = f'https://maps.googleapis.com/maps/api/place/details/json?key={api_key}&place_id={place_id}&fields=opening_hours&language=ko'

    try:
        response = requests.get(details_url)
        data = response.json()
        opening_hours = data.get('result', {}).get('opening_hours', '운영 시간을 제공하지 않는 장소입니다')

        if isinstance(opening_hours, dict):
            weekday_text = opening_hours['weekday_text']
            weekday_hours = {}
            for day in weekday_text:
                day_name, hours = day.split(': ', 1)
                weekday_hours[day_name] = hours
            return weekday_hours
        else:
            return opening_hours

    except Exception:
        return '운영 시간을 제공하지 않는 장소입니다'

def get_photo_url(photo_reference, max_size=200):
    load_dotenv()
    api_key = os.getenv("api_key")
    base_url = 'https://maps.googleapis.com/maps/api/place/photo'

    params = {
        'key': api_key,
        'photoreference': photo_reference,
        'maxwidth': max_size,
        'maxheight': max_size
    }

    photo_url = f'{base_url}?{"&".join([f"{key}={value}" for key, value in params.items()])}'
    return photo_url