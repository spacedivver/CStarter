from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from bs4 import BeautifulSoup

import requests
import time

BASE_URL = 'https://jasoseol.com'

def get_company_list(driver):
  load_recruitment_calendar(driver)

  soup = BeautifulSoup(driver.page_source, 'html.parser')
  company_links = soup.find_all("a", class_="company")
  count = 0

  for company in company_links:
      print("[", count, "] ")
      count += 1
      
      href = company.get("href")
      employment_id = company.get("employment_company_id")

      calendar_label = company.find("div", class_="calendar-label")
      # calendar_label_text = calendar_label.get_text(strip=True) if calendar_label else "N/A"

      company_name_div = company.find("div", class_="company-name")
      company_name = company_name_div.get_text(strip=True) if company_name_div else "N/A"

      print(f"Link: {href}, Employment ID: {employment_id}")
      # print(f"Calendar Label: {calendar_label_text}")
      print(f"Company Name: {company_name}")
      print("-" * 50)

      JOB_OPENING_URL = BASE_URL + href
      get_cover_letter(JOB_OPENING_URL)

def load_recruitment_calendar(driver):
  driver.get(BASE_URL + '/recruit')

  try:
    # 직무 선택
    job_select = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.XPATH, '/html/body/div/div[1]/div[2]/div/div[2]/div/div[2]/div/div[5]/div[1]/input'))
    )
    driver.execute_script("arguments[0].scrollIntoView();", job_select)
    job_select.click()

    # IT/인터넷 분류 선택
    it_category_select = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.XPATH, '/html/body/div/div[1]/div[2]/div/div[2]/div/div[4]/div[1]/div[2]/div/div/div[2]/div/div[1]/div/div[4]/div[1]/img'))
    )
    driver.execute_script("arguments[0].scrollIntoView();", it_category_select)
    it_category_select.click()

    # 직무 선택 종료
    driver.execute_script("arguments[0].scrollIntoView();", job_select)
    job_select.click()
  except Exception as e:
    print("화면 로딩 오류:", e)

def get_cover_letter(JOB_OPENING_URL, driver):
  driver.get(JOB_OPENING_URL)

  main_window_handle = driver.current_window_handle

  for handle in driver.window_handles:
    if handle != main_window_handle:
        driver.switch_to.window(handle)
        driver.close()  # 팝업 창 닫기
        break

  driver.switch_to.window(main_window_handle)

  try:
    element = driver.find_element(By.CSS_SELECTOR, "#__next > div > div:nth-child(1) > div > div.w-full.flex.justify-center.bg-gray-100.pb-\[80px\] > div > div > div > ul")
    # print(element.text)
    # print(element.get_attribute('outerHTML'))

    soup = BeautifulSoup(element.get_attribute('outerHTML'), 'html.parser')

    # 모든 채용 공고 항목을 찾음
    job_list = soup.select('ul.w-full > li')

    # 각 항목에서 필요한 정보를 추출
    for job in job_list:
      position = job.select_one('span.text-gray-700').text.strip()  # 직무
      department = job.select_one('span.text-gray-900').text.strip()  # 부서
      applicants = job.select_one('span.text-gray-700').find_next('span').text.strip()  # 지원자 수
      
      button = driver.find_element(By.XPATH, "//button[text()='자소서 문항 보기']")
      actions = ActionChains(driver)
      actions.move_to_element(button).perform()

      # # 호버 후 나타나는 데이터 추출 (예시로 텍스트 출력)
      # hover_data = driver.find_element(By.XPATH, '//div[@class="hover-appear-class"]').text
      # print(f"호버 후 나타나는 데이터: {hover_data}")

      print(f"직무: {position}, 부서: {department}, 지원자 수: {applicants}")
  except:
    print("데이터를 찾을 수 없습니다.")

def main():
  driver = webdriver.Chrome()
  # get_company_list(driver)
  get_cover_letter('https://jasoseol.com/recruit/95378', driver)
  driver.quit()

if __name__ == "__main__":
    main()
