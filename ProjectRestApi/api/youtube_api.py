from flask_restful import Resource, reqparse
import os
from google.oauth2 import service_account
from googleapiclient.discovery import build

class Youtube(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        parser.add_argument('address', location='args')
        args = parser.parse_args()
        place = args['address']
        print(place)
        results = self.searchtube(place)
        return results

    def searchtube(self, query):
        # 서비스 계정 키를 로드하여 구글 클라우드에 인증 scoped_credentials

        # 환경변수에 서비스키 등록했을시
        credentials_path = os.environ['GOOGLE_APPLICATION_CREDENTIALS']  # 프로젝트 ID,Private Key정보가 있는 .json파일의 경로

        credentials = service_account.Credentials.from_service_account_file(credentials_path)
        scoped_credentials = credentials.with_scopes(['https://www.googleapis.com/auth/youtube'])

        # youtube크롤링 코드
        results = []
        videourl = 'https://www.youtube.com/embed/'
        youtube = build('youtube', 'v3', credentials=scoped_credentials)  # 인증 객체를 여기에 담기

        search_query = f'{query} 여행'  # 여행과 관련된 검색어를 포함하여 검색
        request = youtube.search().list(part='snippet', q=search_query,
                                        maxResults=5).execute()  # https://googleapis.github.io/google-api-python-client/docs/dyn/youtube_v3.search.html

        for item in request['items']:
            if 'videoId' in item['id']:  # 동영상이 있는 것만 추출
                results.append({'id': videourl + item['id']['videoId'], 'title': item['snippet']['title'],
                                'description': item['snippet']['description'],
                                'thumbnail': item['snippet']['thumbnails']['default']['url'],
                                'link': item['snippet']})

        return results