import sys
import json
from openai import OpenAI
import os
import mysql.connector
from dotenv import load_dotenv
from datetime import datetime

load_dotenv()
api_key = os.getenv('OPENAI_API_KEY')
db_host = os.getenv('DB_HOST')
db_port = int(os.getenv('DB_PORT'))
db_user = os.getenv('DB_USER')
db_password = os.getenv('DB_PASSWORD')
db_name = os.getenv('DB_NAME')

client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")

#DB 연결
def connect_to_database():
    try:
        conn = mysql.connector.connect(
            host=db_host,
            port=db_port,
            user=db_user,
            password=db_password,
            database=db_name
        )
        print("데이터베이스 연결 성공")
        return conn
    except mysql.connector.Error as err:
        print(f"데이터베이스 연결 실패: {err}")
        return None

# cover_letter_questions의 테이블의 질문 데이터 가져오기 (40번째줄 바꾸기)
def get_cover_letter_questions(cursor, rno, number,question_type):
    query = """
        SELECT cqno, clno, rno, number, question_type, question, answer
        FROM cover_letter_question_gunho
        WHERE rno = %s AND question_type = %s AND number = %s
        ORDER BY number ASC
    """
    cursor.execute(query, (rno, number,question_type))
    results = cursor.fetchall()
    question=[item['question'] for item in results]
    print(f"-->results:{results}, -->count{count}")
    print(f"-->{question}")
    return results

# cover_letter_questions의 테이블의 질문 데이터 가져오기 (40번째줄 바꾸기)
def get_jobs(cursor,jno,cpno):
    query = """
        SELECT type
        FROM job
        WHERE jno = %s AND question_type = %s
        ORDER BY number ASC
    """
    cursor.execute(query, (jno,cpno))
    result_type = cursor.fetchall()
    print(f"{result_type}")
    return result_type


# 피드백 및 점수 생성
def generate_feedback(client, question,answer,company,):
    messages = [
        {"role": "system", "content": "너는 면접관이야."},
        {"role": "user", "content": f"""
면접질문:{question} 이거 일때, 지원자의 답변: {answer} 이거였어. 

위 답변에 대해 다음 4가지 항목으로 평가와 피드백을 작성해줘:
1. 직무역량 (25점 만점)
2. 직업 기초 능력(논리성) (25점 만점)
3. 로열티 (기업 비전 및 사업 이해도) (25점 만점)
4. 인성 (핵심 가치 및 회사 인재상 적합성) (25점 만점)

각 항목에 점수와 간단한 피드백을 적어줘. 마지막으로 전체 점수를 100점 만점 기준으로 평가하고, 개선 사항도 포함해줘.
        """}
    ]
    try:
        response = client.chat.completions.create(
            model="gpt-4o",
            messages=messages,
            max_tokens=300,
            temperature=0.7
        )
        return response.choices[0].message.content.strip()
    except Exception as e:
        print(f"GPT API 호출 실패: {e}")
        return None

# 피드백 삽입
def insert_feedback(cursor, cqno, feedback):
    query = """
        UPDATE cover_letter_question
        SET feedback = %s
        WHERE cqno = %s
    """
    cursor.execute(query, (feedback, cqno))

# 리포트 삽입
def insert_report(cursor, rno, mno, score, content):
    query = """
        INSERT INTO report (rno, mno, score, content, created_at, company_name, job)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
    """
    cursor.execute(query, (rno, mno, score, content, datetime.now()))


# 메인 프로세스
def process_reports():
    conn = connect_to_database()
    if not conn:
        return

    cursor = conn.cursor(dictionary=True)
    rno = 25  # 리포트 키값
    mno = 9999  # 유져 키값
    jno = 9999 #직무 키값
    number=1 #초기 cover_letter_question 에서 number은 1로 시작

    try:
        # 처리할 rno 가져오기
        questions = get_cover_letter_questions(cursor, rno, number,question_type=0)

        #질문 개수
        print(f"가져온 질문 수: {count}")



        cursor.execute("SELECT DISTINCT rno FROM cover_letter_question")
        rno_list = [row["rno"] for row in cursor.fetchall()]

        for rno in rno_list:
            # 질문 가져오기
            # questions = get_questions(cursor, rno)
            if not questions:
                continue
            answers = []
            feedbacks = []
            total_score = 0
            num_questions = len(questions)

            for question in questions:
                cqno = question["cqno"]
                answer = question["answer"]

                # 피드백 및 점수 생성
                feedback = generate_feedback(client, answer)
                if feedback:
                    insert_feedback(cursor, cqno, feedback)
                    feedbacks.append(feedback)

                    # 점수 추출
                    try:
                        score = int(feedback.split("전체 점수:")[1].split("/")[0].strip())
                        total_score += score
                    except Exception:
                        print("점수 추출 실패")

                answers.append(answer)

            # 평균 점수 계산
            average_score = total_score / num_questions if num_questions > 0 else 0

            # 리포트 내용 생성
            content = f"총 점수: {total_score}점 / 평균 점수: {average_score}점\n\n" + "\n".join(feedbacks)

            # 리포트 삽입
            insert_report(cursor, rno, mno, total_score, content)
            print(f"리포트 생성 완료 - rno: {rno}")

        conn.commit()

    except Exception as e:
        print(f"에러 발생: {e}")
    finally:
        cursor.close()
        conn.close()
        print("데이터베이스 연결 종료")

if __name__ == "__main__":
    conn = connect_to_database()
    cursor = conn.cursor(dictionary=True)
    questions, count=get_cover_letter_questions(cursor,25,0)




# # 평가 항목은 4가지
# def 피드백 만드는 gpt AI 모델 

# # cover_letter_question 테이블에서 number가 1인 것이면서 ... question_type이 0인것 부터 가져오고, 현재 rno와 clno 는 가져와서 question과 answer를 보고 판단해서 def 피드백 api를 통해 피드백을 생성한다. 
# def get_question_type0(cursor):

# # cover_letter_question 테이블에서 number가 1인 것이면서 ... question_type이 1인것 부터 가져오고, 현재 rno와 clno 는 가져와서 question과 answer를 보고 판단해서 def 피드백 api를 통해 피드백을 생성한다. 
# def get_question_type1(cursor):

# # 초기에 들어간 cover_letter_question 에서 같은 clno rno 의 number 갯수가 결국 사용자가 선택한 질문 개수이다. 즉 3개 라면 question_type이 0이면서 number 1 부터 시작하고 그 이후에 는 number는 1이지만 question type은 1인 꼬리질문에 대해 평가한다.
# #question_type 가 0, 1 인 것을 각 각 평가해서 api를 통해 점수가 생기면 그것을 계속 더해야 하고 나중에 총 질문 개수로 나눠서 점수를 산정해준다. 그리고 report의 평가내용들을 계속 듣고 최종적인 content를 생성해서 답변해준다.
# def main 




# 유져 아이디의 고유키인 mno 값을 받아서  (mno->9999, rno->25라 가정)
# 보고서 아이디의 고유키인 rno 값을 받아서 

# table report 쪽에서 mno and rno 에 매치 되는 맞는 보고서 선택, 그 이후 다음 테이블인
# cover_letter_question 테이블에 rno가 25 이면서 AND question_type이 0인 Question을 가져오고 

#사용자가 다 응답 할때 까지 기다린다.

#그리고 사용자가 대답한 answer이 rno가 25이면서 question_type이 0이면서 첫 질문이였으면 number가 1인 상태로 쌓인다.
#그리고 ai 모델이 이 답변을 보고 피드백을 남긴다. "~~이렇게 답변 했으면 더 좋았을 것 같다. " 여기서 핵심적인 것은 평가항목인데
#평가항목은 4가지로 1. 직무역량 2. 직업 기초 능력(논리성) 3. 로열티 (지원 기업 비전에 공감, 사업이해도) 4. 인성 (회사 인재상, 핵심가치에 어울리는 답변)
#위 4가지 항목으로 질문자의 답변이  각 25점 배점을 주고 1질문에 100점 만점기준으로 몇점인지 기록하고 있다가 만약 총 질문이 4개 였으면 400점 만점에 몇점 이렇게 나온다.
#그렇다면 이때 360점을 받았다면 질문이 4개였으니 나누기 4로 해서 90점 이라고 평가가 되고 이건 report 테이블에 저장된다. 그리고 총 평가내용 역시 report 테이블에 저장이 된다.

