import requests,json,os
from dotenv import load_dotenv

def find_nearby_places(api_key, location, radius=2500, keyword='hotel', max_results=15):
    api_url = f'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={location}&radius={radius}&keyword={keyword}&key={api_key}&language=ko'

    try:
        response = requests.get(api_url)
        data = response.json()
        places = sorted(data.get('results', []), key=lambda x: x.get('rating', 0), reverse=True)[:max_results]

        place_info_list = []

        with open('hotel.json', 'w', encoding='utf8') as f:
            for place in places:
                name = place['name']
                vicinity = place.get('vicinity', 'N/A')
                rating = place.get('rating', 'N/A')
                latitude = place['geometry']['location']['lat']
                longitude = place['geometry']['location']['lng']
                place_id = place['place_id']

                print(f'호텔명: {name}')
                print(f'주소: {vicinity}')
                print(f'평점: {rating}')
                print(f'위도: {latitude}')
                print(f'경도: {longitude}')
                print(f'place_id: {place_id}')

                photos = place.get('photos', [])
                if photos:
                    photo_reference = photos[0].get('photo_reference', '')
                    if photo_reference:
                        print('이미지 URL:', get_photo_url(photo_reference))
                        print()
                        place_info = {
                            'name': name,
                            'address': vicinity,
                            'rating': rating,
                            'lat': latitude,
                            'lng': longitude,
                            'place_id': place_id,
                            'img_URL': get_photo_url(photo_reference)
                        }
                        place_info_list.append(place_info)
                else:
                    print()

            f.write(json.dumps(place_info_list, indent=4, ensure_ascii=False))

            place_ids = [place['place_id'] for place in places]
            print("모든 place_id:", place_ids)
            # review(place_ids)
        print(place_info_list)

        return place_info_list

    except Exception as e:
        print(f'에러: {e}')

def get_photo_url(photo_reference, max_width=400):
    load_dotenv()
    api_key = os.getenv("api_key")
    base_url = 'https://maps.googleapis.com/maps/api/place/photo'

    params = {
        'key': api_key,
        'photoreference': photo_reference,
        'maxwidth': max_width,
    }

    photo_url = f'{base_url}?{"&".join([f"{key}={value}" for key, value in params.items()])}'
    return photo_url



