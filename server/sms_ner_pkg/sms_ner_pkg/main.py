# -*- coding: utf-8 -*-
import sys
from date_detector import DateDetector as DateDetector
from time_detector import TimeDetector as TimeDetector
# from location_detector import loc_detector as location_detector
from type_detector import type_detector as type_detector

def get_package_result(message):
    package_dic = {}
    package_result = []

    # type, count
    message_type, infected_count = type_detector(message)
    package_result.append(['type', message_type])
    package_result.append(['count', infected_count])
    package_dic['type'] = message_type
    package_dic['count'] = infected_count

    # date
    dateDetector = DateDetector()
    date = dateDetector.disaster_message_date_detector(message)
    package_result.append(['date', date])
    package_dic['date'] = date

    # time
    timeDetector = TimeDetector()
    times = timeDetector.time_detector(message)
    time = timeDetector.disaster_message_time_detector(times, message)
    package_result.append(['time', time])
    package_dic['time'] = time

    # location
    # package_result["location"] = location_detector(message)

    # print('RESULT OF get_package_result()')
    # print('{result}\n'.format(result=package_result))

    return package_result

# 메세지 받아와서 type, date, time, location 내뱉기
get_package_result(sys.argv[1])

if __name__ == '__main__':
    print(get_package_result(sys.argv[1]))