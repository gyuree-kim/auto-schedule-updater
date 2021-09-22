# -*- coding: utf-8 -*-
import sys
from date_detector import DateDetector as DateDetector
from time_detector import TimeDetector as TimeDetector
# from location_detector import loc_detector as location_detector
from type_detector import type_detector as type_detector

def get_package_result(message):
    package_result = {}

    # type, count
    message_type, infected_count = type_detector(message)
    package_result["type"] = message_type
    package_result["count"] = infected_count

    # date
    dateDetector = DateDetector()
    # package_result["date"] = dateDetector.date_detector(message)
    date = dateDetector.disaster_message_date_detector(message)
    print(date)
    package_result["date"] = date

    # time
    timeDetector = TimeDetector()
    # time = timeDetector.time_detector(message)
    times = timeDetector.time_detector(message)
    time = timeDetector.disaster_message_time_detector(times, message)
    print(time)
    package_result["times"] = time

    # location
    # package_result["location"] = location_detector(message)

    return package_result

# 메세지 받아와서 type, date, time, location 내뱉기
if __name__ == '__main__':
    print(get_package_result(sys.argv[1]))