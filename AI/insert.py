import os
import pandas as pd
import mysql.connector
from dotenv import load_dotenv

# .env 파일에서 데이터베이스 정보 로드
load_dotenv()
db_host = os.getenv('DB_HOST')
db_port = int(os.getenv('DB_PORT'))
db_user = os.getenv('DB_USER')
db_password = os.getenv('DB_PASSWORD')
db_name = os.getenv('DB_NAME')

# MySQL 데이터베이스 연결
connection = mysql.connector.connect(
    host=db_host,
    port=db_port,
    user=db_user,
    password=db_password,
    database=db_name
)
cursor = connection.cursor()

# 루트 폴더 기준으로 파일 경로 설정
file_path = './신규상장기업현황.xls'  # 루트 폴더에 위치한 파일

# Excel 파일 읽기 (engine 지정)
df = pd.read_excel(file_path, engine="openpyxl")

# 필요한 컬럼만 추출
df_filtered = df[['회사명', '업종']]

# 데이터베이스에 삽입
insert_query = """
    INSERT INTO corporate_values (company_name, industry)
    VALUES (%s, %s)
"""

for index, row in df_filtered.iterrows():
    company_name = row['회사명']
    industry = row['업종']
    
    # INSERT 실행
    cursor.execute(insert_query, (company_name, industry))

# 변경 사항 커밋 및 연결 종료
connection.commit()
cursor.close()
connection.close()

print("데이터 삽입 완료")
