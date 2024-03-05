from flask import request,send_file
from flask_restful import Resource
from openai import OpenAI
import urllib.request
import openai

class Images(Resource):
    def post (self):
        try:
          data = request.get_json()
          prompt = data.get('prompt', '')

          client = OpenAI()

          response = client.images.generate(
            model="dall-e-3",
            prompt=prompt,
            size="1024x1024",
            response_format='url',#기본값
            n=1,
          )
          print(response)#ImagesResponse(created=1704435764, data=[Image(b64_json=None, revised_prompt='A small and adorable Maltese dog', url='https://oaidalleapiprodscus.blob.core.windows.net/private/org-wI2J6CMeaXTGF25ZJj0rvFat/user-vtVdVK3jl7jn6cogM2vAi7Mn/img-BqIRIm5spkNnEGeywWLLWfnE.png?st=2024-01-05T05%3A22%3A44Z&se=2024-01-05T07%3A22%3A44Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2024-01-04T08%3A56%3A10Z&ske=2024-01-05T08%3A56%3A10Z&sks=b&skv=2021-08-06&sig=U9mFAoC1e6e9Ec5buJJzy7UciFPCu6GQTBjuWr0AhMo%3D')])
          #방법1)1시간이 지나기전에 로컬에 파일로 저장
          image_url = response.data[0].url#1시간만 유효

          urllib.request.urlretrieve(image_url, filename=prompt)

          # 이미지 파일을 Flask response에 첨부하여 반환
          return send_file(path_or_file=prompt, mimetype='image/jpeg')


        except openai.error.OpenAIError as e:

            print(f"OpenAI API returned an error: {e}")

            return {"message": "OpenAI API error"}
