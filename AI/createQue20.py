import pymysql
import json
from openai import OpenAI
import os
from dotenv import load_dotenv

# .env 파일에서 API 키와 DB 정보 로드
load_dotenv()
api_key = os.getenv('OPENAI_API_KEY')
db_host = os.getenv('DB_HOST')
db_port = int(os.getenv('DB_PORT'))
db_user = os.getenv('DB_USER')
db_password = os.getenv('DB_PASSWORD')
db_name = os.getenv('DB_NAME')

client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")


# GPT-4o 모델 사용 함수 설정
def generate_questions(company, role, intro_questions, tech_stack, intro_text):
    # 메시지 형식으로 프롬프트 작성
    messages = [
        {"role": "system", "content": f"너는 지금부터 '{company}'이라는 회사의 '{role}'의 면접관이야."},
        {"role": "user", "content": f"다음은 자기소개서 문항과 지원자의 기술스택, 자기소개서 내용이야. 이 정보를 바탕으로 20개의 면접 질문을 만들어줘.\n\n"
                                    f"자기소개서 문항:\n{intro_questions}\n\n"
                                    f"기술스택:\n{tech_stack}\n\n"
                                    f"자기소개서 내용:\n{intro_text}\n\n"
                                    "면접 질문 20개:"}
    ]
    
    # ChatCompletion API를 사용해 질문 생성
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=messages,
        max_tokens=1000,
        temperature=0.7
    )

    # 모델 응답에서 질문 목록을 추출
    content = response.choices[0].message.content.strip()
    # 공백 질문 제거
    return [q for q in content.split("\n") if q.strip()]

# 데이터 로드
with open('question.json', 'r', encoding='utf-8') as q_file:
    question_data = json.load(q_file)

# 첫 번째 항목 선택
question_data = question_data[0]

company_name = question_data.get("company_name", "회사명 없음")
role_name = question_data.get("role_name", "직무 없음")
intro_questions = question_data.get("intro_questions", [])

# 사용자 답변 로드
with open('answer.txt', 'r', encoding='utf-8') as a_file:
    answer_text = a_file.read()

# 기술스택과 자기소개서 부분 추출
tech_stack = "Spring, SQL"  # 기술스택 추출 (예시로 지정)
intro_text = answer_text  # 전체 텍스트를 사용하거나 필요한 부분을 추출

# 질문 생성
questions = generate_questions(company_name, role_name, intro_questions, tech_stack, intro_text)

# MySQL에 질문 삽입 함수 정의
def insert_questions_to_db(questions, db_host, db_port, db_user, db_password, db_name):
    try:
        # MySQL 연결
        connection = pymysql.connect(
            host=db_host,
            port=db_port,
            user=db_user,
            password=db_password,
            database=db_name,
            charset='utf8mb4'
        )
        
        cursor = connection.cursor()
        
        # 질문 삽입
        insert_query = "INSERT INTO cover_letter_question (number, question) VALUES (%s, %s)"
        for idx, question in enumerate(questions, start=1):
            cursor.execute(insert_query, (idx, question))
        
        # 변경 사항 커밋
        connection.commit()
        print("질문이 데이터베이스에 성공적으로 삽입되었습니다.")
        
    except Exception as e:
        print(f"데이터베이스 삽입 중 오류 발생: {e}")
    finally:
        connection.close()

# 질문 삽입 실행
if questions:
    insert_questions_to_db(questions, db_host, db_port, db_user, db_password, db_name)
else:
    print("유효한 질문이 없어 데이터베이스에 삽입되지 않았습니다.")

# 결과 생성 및 저장
output_data = {
    "회사명": company_name,
    "직무": role_name,
    "이름": "지원자 이름",  # answer.txt에서 이름을 추출하여 사용할 수 있음
    "질문 리스트": questions
}

with open('create.json', 'w', encoding='utf-8') as c_file:
    json.dump(output_data, c_file, ensure_ascii=False, indent=4)

print("create.json 파일이 성공적으로 생성되었습니다.")
