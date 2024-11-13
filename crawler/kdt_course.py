# from selenium import webdriver
# from selenium.webdriver.common.by import By
# from selenium.webdriver.support.ui import WebDriverWait
# from selenium.webdriver.support import expected_conditions as EC
# from bs4 import BeautifulSoup

# import time
# import requests

# BASE_URL = "https://boottent.com/camps"


# def execute_script(driver, x_path):
#     select = WebDriverWait(driver, 10).until(
#         EC.element_to_be_clickable((By.XPATH, x_path))
#     )
#     driver.execute_script("arguments[0].scrollIntoView();", select)
#     driver.execute_script("arguments[0].click();", select)


# def load_kdt_web_course_list(driver):
#     driver.get(BASE_URL + "?categories=all")

#     execute_script(
#         driver, "/html/body/main/section/div[3]/div[1]/div/ul/li[6]/div/button"
#     )  # 내일배움카드 여부 선택 박스 열기
#     execute_script(
#         driver,
#         "/html/body/main/section/div[3]/div[1]/div/ul/li[6]/div/div/div[1]/div[1]/label",
#     )  # 내일배움카드 선택
#     execute_script(
#         driver, "/html/body/main/section/div[3]/div[1]/div/ul/li[6]/div/button"
#     )  # 내일배움카드 여부 선택 박스 닫기


# def crawl(driver):
#     url = "https://boottent.com/camps?categories=all"

#     response = requests.get(url)
#     soup = BeautifulSoup(response.text, "html.parser")

#     lis = soup.select("body > main > section > div:nth-child(5) > div > ul > li")
#     for li in lis:
#         print(li.prettify())


# def main():
#     driver = webdriver.Chrome()

#     # load_kdt_web_course_list(driver)
#     crawl(driver)

#     driver.quit()


# if __name__ == "__main__":
#     main()
