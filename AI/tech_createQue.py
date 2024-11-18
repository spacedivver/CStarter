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

# 데이터베이스에서 필요한 정보 가져오는 함수
def fetch_data_from_db(db_host, db_port, db_user, db_password, db_name):
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
        
        cursor = connection.cursor(pymysql.cursors.DictCursor)
        
        cursor.execute("""SELECT cli.content AS content
                        FROM cover_letter_item cli
                        INNER JOIN cover_letter_answer cla ON cli.cino = cla.cino
                        INNER JOIN cover_letter cl ON cla.clno = cl.clno
                        INNER JOIN user u ON cl.mno = u.mno
                        WHERE u.mno = 9999;""")
        intro_questions = [row["content"] for row in cursor.fetchall()]
        
        cursor.execute("""SELECT cla.answer AS answer
                        FROM cover_letter_answer cla
                        INNER JOIN cover_letter cl ON cla.clno = cl.clno
                        INNER JOIN user u ON cl.mno = u.mno
                        WHERE u.mno = 9999;""")
        intro_text = [row["answer"] for row in cursor.fetchall()]
        
        return intro_questions, intro_text
        
    except Exception as e:
        print(f"데이터베이스 조회 중 오류 발생: {e}")
        return None, None, [], None
    finally:
        connection.close()

# GPT-4o 모델 사용 함수 설정
def generate_questions(company, role, tech_stack, QueNum):
    # 메시지 형식으로 프롬프트 작성
    messages = [
        {"role": "system", "content": f"너는 지금부터 '{company}'이라는 회사의 '{role}'의 면접관이야."},
        {"role": "user", "content": f"다음은 지원자의 기술스택이야. 이 정보를 바탕으로 {QueNum}개의 면접 질문을 만들어줘.\n\n"
                                    f"기술스택:\n{tech_stack}\n\n"
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

# 데이터베이스에서 불러오기
intro_questions, intro_text = fetch_data_from_db(db_host, db_port, db_user, db_password, db_name)

company_name="test 회사명"
role_name="test 직무"
QueNum=20

# 기술스택 지정
tech_stack = "Spring, SQL"

if not company_name or not role_name or not intro_questions or not intro_text:
    print("데이터 오류")
else:
    # 질문 생성
    questions = generate_questions(company_name, role_name, tech_stack, QueNum)

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
            insert_query = "INSERT INTO cover_letter_question (clno, number, question_type, question) VALUES (%s, %s, 1, %s)"
            for idx, question in enumerate(questions, start=1):
                cursor.execute(insert_query, (9999, idx, question))
            
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
        "질문 리스트": questions
    }

    with open('create.json', 'w', encoding='utf-8') as c_file:
        json.dump(output_data, c_file, ensure_ascii=False, indent=4)

    print("create.json 파일이 성공적으로 생성되었습니다.")


# 출력 테스트 부분

# print(f"회사명 (company_name): {company_name}")
# print(f"직무명 (role_name): {role_name}")
# print(f"자기소개서 질문 (intro_questions): {intro_questions}")
# print(f"자기소개서 답변 (intro_text): {intro_text}")
