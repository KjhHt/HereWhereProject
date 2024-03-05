from flask import request, jsonify
from flask_restful import Resource
from openai import OpenAI
import os
import time


class AssistantResource(Resource):
    def __init__(self):
        super().__init__()
        self.api_key = "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"  # 하드코딩된 API 키
        self.client = OpenAI(api_key=self.api_key)
        self.thread_id = None
        self.assistant_id = "asst_OLq3w140yAC04nWeHgU19dc8"  # 챗봇 Assistant ID 설정

    def post(self):
        data = request.get_json()  # Vue.js에서 보낸 데이터 받기
        prompt = data.get('prompt')  # 사용자 입력 메시지

        # 사용자가 입력한 메시지 콘솔에 출력
        print("사용자 입력:", prompt)

        response = self.send_message(prompt)  # 사용자 입력에 대한 챗봇 응답 받기

        # 챗봇 응답 메시지 콘솔에 출력
        print("챗봇 응답:", response)

        return jsonify({'response': response})  # Vue.js로 챗봇 응답 전송

    def send_message(self, prompt):
        # 챗봇에 메시지 보내기
        if not self.thread_id:
            # 스레드 ID가 없으면 새로운 스레드 생성
            thread = self.client.beta.threads.create()
            self.thread_id = thread.id

        message = self.client.beta.threads.messages.create(
            thread_id=self.thread_id,
            role="user",
            content=prompt
        )

        # 챗봇 응답 받기
        run = self.client.beta.threads.runs.create(
            thread_id=self.thread_id,
            assistant_id=self.assistant_id,
        )

        while run.status != "completed":
            time.sleep(1)
            run = self.client.beta.threads.runs.retrieve(thread_id=self.thread_id, run_id=run.id)

        # 챗봇이 보낸 메시지 가져오기
        messages = self.client.beta.threads.messages.list(thread_id=self.thread_id).data
        response = None
        for msg in reversed(messages):
            if msg.role == "assistant":
                response = msg.content[0].text.value
                break

        # 챗봇 응답이 없을 경우에 대한 처리
        if response is None:
            response = "챗봇으로부터 응답이 없습니다."

        # 챗봇 응답 메시지 콘솔에 출력
        print("챗봇 응답:", response)

        return response

