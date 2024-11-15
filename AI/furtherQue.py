import json
from openai import OpenAI
import os
from dotenv import load_dotenv

# .env 파일에서 API 키 로드
load_dotenv()
api_key = os.getenv('OPENAI_API_KEY')
client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")



with open('answer.txt', 'r', encoding='utf-8') as a_file:
    answer_text = a_file.read()

messages = [
        {"role": "system", "content": "너는 면접관이야."},
        {"role": "user", "content": f"지원자의 답변: {answer_text}\n\n위 답변에 대해 추가로 1개의 질문을 만들어줘."}
    ]
response = client.chat.completions.create(
    model="gpt-4o",
    messages=messages,
    max_tokens=100,
    temperature=0.7
)


follow_up_question = response.choices[0].message.content.strip()
print("추가 질문:", follow_up_question)


with open('furtherQue.json', 'w', encoding='utf-8') as c_file:
    json.dump(follow_up_question, c_file, ensure_ascii=False, indent=4)

print("furtherQue.json 파일이 성공적으로 생성되었습니다.")
