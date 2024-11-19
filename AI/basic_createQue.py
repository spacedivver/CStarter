import sys
import pymysql
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

company_name = sys.argv[1]  # 회사명
role_name = sys.argv[2]     # 직무명
que_num = int(sys.argv[3])  # 생성할 질문 수
clno = int(sys.argv[4])     # 자기소개서 번호

# 데이터베이스에서 필요한 정보 가져오는 함수
def fetch_data_from_db(db_host, db_port, db_user, db_password, db_name, clno):
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
        
        # clno에 해당하는 문항과 답변 가져오기
        cursor.execute("""SELECT cli.content AS content
                        FROM cover_letter_item cli
                        INNER JOIN cover_letter_answer cla ON cli.cino = cla.cino
                        INNER JOIN cover_letter cl ON cla.clno = cl.clno
                        WHERE cl.clno = %s;""", (clno,))
        intro_questions = [row["content"] for row in cursor.fetchall()]
        
        cursor.execute("""SELECT cla.answer AS answer
                        FROM cover_letter_answer cla
                        INNER JOIN cover_letter cl ON cla.clno = cl.clno
                        WHERE cl.clno = %s;""", (clno,))
        intro_text = [row["answer"] for row in cursor.fetchall()]
        
        return intro_questions, intro_text
        
    except Exception as e:
        print(f"데이터베이스 조회 중 오류 발생: {e}")
        return None, None
    finally:
        connection.close()

# 데이터베이스에서 데이터 가져오기
intro_questions, intro_text = fetch_data_from_db(db_host, db_port, db_user, db_password, db_name, clno)

if not company_name or not role_name or not intro_questions or not intro_text:
    print("데이터 오류")
else:
    # GPT 질문 생성 함수
    def generate_questions(company, role, intro_questions, intro_text, que_num):
        messages = [
            {"role": "system", "content": f"너는 지금부터 '{company}'이라는 회사의 '{role}'의 면접관이야."},
            {"role": "user", "content": f"다음은 자기소개서 문항과 지원자의 기술스택, 자기소개서 내용이야. 이 정보를 바탕으로 {que_num}개의 면접 질문을 만들어줘.\n\n"
                                        f"자기소개서 문항:\n{intro_questions}\n\n"
                                        f"자기소개서 내용:\n{intro_text}\n\n"
                                        "면접 질문 {que_num}개:"}
        ]
        response = client.chat.completions.create(
            model="gpt-4o",
            messages=messages,
            max_tokens=1000,
            temperature=0.7
        )
        content = response.choices[0].message.content.strip()
        return [q for q in content.split("\n") if q.strip()]

    # 질문 생성
    questions = generate_questions(company_name, role_name, intro_questions, intro_text, que_num)

    # 질문 출력
    print({"questions": questions})

    