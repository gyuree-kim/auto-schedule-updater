# -*- coding: utf-8 -*-
import re
from konlpy.tag import Hannanum
from .data_loader import subway_loader as subway_loader
from .data_loader import store_loader as store_loader
from .data_loader import sigu_loader as sigu_loader

han = Hannanum()
def loc_detector(message):
    rex_loc = []    #도로명주소 리스트
    konlp_loc = []  #형태소 파싱 후 주소후보 리스트
    city_loc = []   #도시 이름 리스트
    # subway_loc = []
    # store_loc = []
    #for message in messages:

    # 정규표현식 도로명주소 추출
    road_location = _get_road_address(message)
    if road_location and _get_only_location(road_location): # 도로명 주소가 존재한다면
        rex_loc.append(road_location)
        return road_location

    city_loc = _get_city_location(message)  # 도시 장소 사전탐색

    message = deleteSpecialCharacters(message)
    # 형태소 분석 주소 후보 추출
    konlpy_location_set = _get_locations_by_konlpy(message)
    # 결과값인 set을 list로 변환 후 konlpy_loc에 연결추가
    konlpy_location_list = list(konlpy_location_set)
    for loc in konlpy_location_list:
        if _get_only_location(loc):
            konlp_loc.append(loc)

    konlp_loc.reverse() # 역순으로 정렬 (최근 message일수록 장소 확률 증가)

    store_loc = _get_store_location(konlp_loc)    #간판명 장소 사전탐색
    subway_loc = _get_subway_location(konlp_loc)  #지하철명 장소 사전탐색

    # _location = list(set(rex_loc)) + list(set(subway_loc)) + list(set(store_loc) + list(set(city_loc)))
    print(rex_loc, subway_loc, store_loc)
    _location = list(set(rex_loc + subway_loc + store_loc+ city_loc))
    # location = city_loc.join(_location)
    accuracy = measure_accuracy(_location)
    print("정확도 : ", accuracy)
    return _location
    # return city_loc
    
def _get_store_location(locations):
    store_list = [] # 찾은 store 간판명 리스트
    store_df = store_loader()   # store 데이터 로드

    for location in locations:
        candi_list = store_df[store_df['상호명'].str.contains(location)]   # location 단어가 포함된 상호명 데이터프레임 얻기
        if location in candi_list['상호명'].values:    #상호명 값만 list로 한후 동일 이름 찾기
            store_list.append(location)

    return store_list

def _get_subway_location(locations):
    locations.sort()    # 초성이 바뀔때만 subway파일을 로드하기 위해
    subway_list = []    # 찾은 지하철역 저장
    save_chosung = ''  #이전 로드한 지하철역 초성을 저장
    for location in locations:
        if '출구' in location:
            subway_list.append(location)
        if '가' <= location[0] <= '힣':   #한글로 시작하는 장소만 추출
            if location[-1] == '역': # 마지막이 역으로 끝났을 때
                chosung = _find_chosung(location)    # 해당 location의 초성 찾기
                if save_chosung != chosung: #다른 초성이 나왔을때만 subway데이터 로드
                    if (chosung == "ㄲ") | (chosung == "ㅃ") | (chosung == "ㅆ") | (chosung == "ㅉ") | (chosung == "ㅎ"):
                        continue    #data_loader 오류 수정중... 우선 예외처리
                    save_df = subway_loader(location)
                    save_chosung = chosung
                if location in save_df.values:  #리스트로 변환 후 search
                    subway_list.append(location + "역")  # subway 데이터에 생략된 '역'문자 추가

    return subway_list

def _get_city_location(sentence):
    city_list = []  # 찾은 store 간판명 리스트
    sigu_df = sigu_loader()  # 시, 구 데이터 로드
    words = sentence.split(' ')
    short_name = ["서울시","부산시","대구시","인천시","광주시","대전시","울산시","제주시"]
    long_name = ["서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시","제주특별자치도"]
    si_string=""

    for word in words:
        # si_df = sigu_df[sigu_df['SI'].str.contains(word)]
        # gu_df = sigu_df[sigu_df['GU'].str.contains(word)]  + short_name
        for si_name in list(set(list(sigu_df['SI']))) + short_name:
            if si_name in word:
                city_list.append(si_name)
                si_string = si_name
        for gu_name in list(sigu_df['GU']):
            if gu_name in word:
                match_si = list(sigu_df[sigu_df['GU']==gu_name]['SI'])
                # short_match_si = short_name[long_name.index(match_si[0])]
                # match_si.append(short_match_si)
                print(gu_name)
                match_si.append("")
                if si_string in match_si:
                    city_list.append(gu_name)

    return city_list

def measure_accuracy(candi_list):
    answer_list = [#"롯시", "집", "학교앞", "코엑스", "왕십리역", "3번출구", "스타벅스", "투썸", "탐앤탐스", #input.txt 데이터
                   #"설악산", "고속터밀널역", "부평역", "잇빗 511호", "2001아울렛", "자연초등학교", "도서관",
                   "부평역", "인천공항", "공항철도", "영종역",
                   "사당역", "서울대입구", "인천", "서울", "녹사평역", "강남역", "9번 출구",
                   "용두", "청량리", "노원", "10번출구",
                   "동문회관", "신정문", "제1의학관", "중도",
                   "혜리꽃케이크", "부평문화로77", "VR게임장", "카페비모",  "2공", "중도", "한양대역", "로비",
                   "수원역", "노보텔", "4번출구", "와플대학", "어묵대학", "AK FOODHALL", "도봉산역"]
    real_answer_list = ["영종역", "서울대입구", "녹사평역", "강남역", "용두역", "중도앞", "부평문화로77", "혜리꽃케이크",
                        "VR게임장", "카페비모", "중도", "수원역", "도봉산역"]
    answer_num = len(answer_list)
    candi_num = len(candi_list)
    real_num = len(real_answer_list)
    correct_num = 0
    r_correct_num = 0
    for candidate in candi_list:
        for answer in answer_list:
            if answer[-1] == '역':  # 마지막이 역으로 끝나면 '역'제거
                answer = answer[:-1]
            if answer in candidate:
                correct_num += 1
    # print(correct_num,"/",answer_num,"= ",correct_num/answer_num*100)
    for candidate in candi_list:
        for r_answer in real_answer_list:
            if r_answer[-1] == '역':
                r_answer = r_answer[:-1]
            if r_answer in candidate:
                r_correct_num += 1
    # print(r_correct_num,"//",real_num, "= ",r_correct_num/real_num*100)
    return correct_num/answer_num*100

def _find_chosung(string):
    # 첫글자 초성 추출
    first_chosung = ""
    CHOSUNG_LIST = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ']
    for w in list(string[0].strip()):
        # 영어인 경우 구분해서 작성함.
        if '가' <= w <= '힣':
            # 588개 마다 초성이 바뀜.
            ch = (ord(w) - ord('가')) // 588
            first_chosung += CHOSUNG_LIST[ch]
    # print(first_chosung)
    return first_chosung

def _get_road_address(sentence):
    road_address = ""    #공백 문자열

    #시도, 시군구 정규표현식 이후에 나머지 도로명주소 정규표현식을 적용한다.
    rex_sigu = re.compile("([가-힣]{2,6}(시|도)).([가-힣]+(시|군|구))")
    rex_ro = re.compile("((\s[가-힣\d\,\.]+(읍|면|동|가|리|구))|).([가-힣A-Za-z·\d~\-\.]+(로|길)).((지하 |공중 |)[\d]+)((\,(\s[\d]+동|)(\s[\d~]+층|)(\s[\d]+호|))|)((\s|)\([가-힣]+동\)|)")
    #시도, 시군구 적용
    sigu = rex_sigu.search(str(sentence))
    #존재한다면 문자열에 추가 후 다음 문자열부터 탐색
    if sigu == None:
        tail_sentence = sentence    # 시, 구 찾지 못했을 때 전체 문자열
    else:
        tail_sentence = sentence[sigu.end():]    #tail_sentence : 시, 구 이후의 나머지 문자열
        road_address += sigu.group()
    #읍면구 로길 상세주소 (동) 정규표현식 적용
    ro = rex_ro.search(str(tail_sentence))
    # 존재할때 문자열에 추가
    if ro != None:
        road_address += ro.group()
    #문자열 반환
    return road_address

def _get_locations_by_konlpy(_sentence):
    sentence = _delete_jamo(_sentence)   #자모 제거
    morphemes = han.analyze(sentence) #형태소 분석
    location_set = set()

    string = "" #공백 문자열 (조사 발견시 앞 형태소 저장 용도)

    for word in morphemes:    #문장 내 어절 단위
        for case in word:   #동일 어절의 형태소 분석 경우의 수
            for unit in case: #해당 경우의 하나의 형태소 단위
                #기타일반명사 바로 추출
                if unit[1] == 'nqq' or unit[1] == 'ncn':
                    if len(unit[0]) > 1:    #2글자 이상인 단어만 후보
                        location_set.add(unit[0])
                # 부사격, 목적격 조사로 앞에 명사 찾기
                elif unit[1] == 'jca' or unit[1] == 'jco':
                    josa_idx = case.index(unit)
                    for i in range(josa_idx):
                        string += case[i][0]
                    if len(string) > 1:    #2글자 이상인 단어만 후보
                        location_set.add(string)
                    string  = ""

    return location_set

# 단어에 포함된 특수문자 제거
def deleteSpecialCharacters(word):
        word = ''.join(char for char in word if char.isalnum())
        return word

def _delete_jamo(_sentence):
    #자음으로, 또는 모음으로만 이뤄진 글자 제거(오타, 감정표현, 초성 등)
    sentence = ""  # 공백 문자열
    not_jamo = re.compile("([^ㄱ-ㅎㅏ-ㅣ~^\"(]+)")    #자음, 모음, 특수문자제거
    not_jamo_list = not_jamo.findall(_sentence)
    # 존재한다면 문자열에 추가 후 다음 문자열부터 탐색
    if not_jamo_list == None:
        sentence = _sentence
    else:
        for i in not_jamo_list:
            sentence += i
    return sentence

def _get_only_location(word):
    if word.isdigit(): return ""
    else : return word
