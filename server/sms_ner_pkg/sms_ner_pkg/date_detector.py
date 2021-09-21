# -*- coding: utf-8 -*-
import time
import re

class DateDetector():
    def __init__(self):
        self.dates = []
        self.words = [] #각 문장별로 문장의 단어들을 저장
        self.dateWords = [
            '오늘', '내일', '모레', '글피', '주말', '휴일'
            '월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일',
            '월욜', '화욜', '수욜', '목욜', '금욜', '토욜', '일욜',
            '이번주', '다음주', '이번 주', '다음 주', '담주'
        ]
        self.targetWords = ['월', '일', '뒤', '후']

    def get_dates(self):
        return self.dates

    def add_date(self, date):
        if date :
            if '뒤' in date or '후' in date:
                self.dates[-1] = self.dates[-1]+date
            else : self.dates.append(date)

    def has_numbers(self, word):
        return any(char.isdigit() for char in word)

    def is_date_format(self, input):
        try:
            time.strptime(input, '%m.%d')
            return True
        except ValueError:
            return False

    # 날짜와 관련된 단어인지 유효성 검사
    def is_valid(self, word, idx):
        if '월' in word or '일' in word:
            return self.has_numbers(word)
        if '뒤' in word or '후' in word:
            if self.has_numbers(word):
                return True
            elif self.words[idx-1] in self.dates:
                return True
        else : return False

    # 단어에 포함된 특수문자 제거
    def delete_special_characters(self, word):
        word = ''.join(char for char in word if char.isalnum())
        return word

    # 각 문장에 포함된 날짜와 관련된 단어를 dates에 추가
    def date_detector(self, message):
        # print(message.split(" ")[0].decode('utf-8').encode('utf-8'))
        message = message.replace('(', ' ')
        message = message.replace('}', ' ')
        words = message.split()
        self.words = words
        for word in words:
            # 정규식 검사
            index = word.index('~') if '~' in word else word.index('-') if '-' in word else 0
            if index != 0: # 기간으로 주어지는 경우
                start, end = word[:index], word[index + 1:]
                if self.is_date_format(start) and self.is_date_format(end):
                    self.add_date(start + "-" + end)
                    continue
            else:
                if "(" in word: word = str(word[:word.index("(")])
                if self.is_date_format(str(word)):
                    self.add_date(word)
                    continue

            # dateWords 내의 단어가 있는지 검사
            done = False
            for dateWord in self.dateWords:
                if dateWord in word:
                    _dateWord = self.delete_special_characters(dateWord) #특수문자 삭제
                    self.add_date(_dateWord)
                    done = True
            if done: continue

            # targetWords 내의 단어가 있는지 검사
            for targetWord in self.targetWords:
                if targetWord in word:
                    idx = words.index(word)
                    if self.is_valid(word,idx):
                        # word = word.decode('utf-8').encode('utf-8')
                        # _word = self.delete_special_characters(word) #특수문자 삭제
                        # self.add_date(_word.split('에')[0]) #조사 삭제
                        print(word.split('에')[0])
                        self.add_date(word.split('에')[0]) #조사 삭제
        return self.get_dates()

    def disaster_message_date_detector(self, message):
        #{월}.{일}.{요일} {월}.{일}.(요일) {월}.{일}(요일) {월}/{일}(요일) {월}월{일}일
        date = ''
        days = ['월', '화', '수', '목', '금', '토', '일']
        _days = ['({day})'.format(day=day) for day in days]
        words = message.split(' ')

        for idx, word in enumerate(words):
            check_day = [True if day in word else False for day in _days]
            has_day = True if True in check_day else False
            
            # 기간이 아닌데 '월'이 두 번 등장한 경우
            if ('월' in date) and ('월' in word) and ('~' not in message):
                break

            # {월}.{일} 또는 {월}/{일} {월}월{일}일 포맷이 포함된 경우
            signs = ['.', '/', '월', '일']
            sign = ''
            for _sign in signs:
                if _sign in word: sign = _sign
            if sign:
                _word = word.split(sign)
                if _word[0].isdigit():
                    if date:
                        date += ' ' + word
                    else:
                        date = word

            # '{월}{sign}{일}까지' 포맷이 포함된 경우 해당 데이터 삭제
            if '까지' in word and word in date:
                date = date.replace(word, '')
                if date[-1] == ' ': date = date[:-1]
                _date = date.split(' ')
                if '월' in _date[-1]:
                    date = date.replace(_date[-1], '')

            # ({요일}) 이 포함된 경우
            if has_day:
                if word not in date:
                    if date: date += ' ' + word
                    else: date = word 
                    break

        return str(date)

if __name__ == '__main__':
    dateDetector = DateDetector()
    message_content = '[서산시청] 5.27.목 19시~21시까지 서산중앙병원 장례식장 별실 방문자는 6월 1일까지 보건소 선별진료소(09~18시까지)에서 검사 바랍니다.'		
    message_content = '[서산시청] 5월 27일(목) 19시~21시까지 서산중앙병원 장례식장 별실 방문자는 6월 1일까지 보건소 선별진료소(09~18시까지)에서 검사 바랍니다.'		
    # print(dateDetector.date_detector(message_content))
    print(dateDetector.disaster_message_date_detector(message_content))