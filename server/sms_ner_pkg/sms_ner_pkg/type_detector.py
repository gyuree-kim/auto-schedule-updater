# -*- coding: utf-8 -*-
import os
import sys

def type_detector(message):
    messageType = ""
    infectedCount = "0"

    if "신규 확진자" in message and "방문자" not in message :
        messageType = "count"
        for i, word in enumerate(message.split()) :
            if ("명" in word) and ("발생" in message.split()[i+1]) :
                infectedCount = word.replace("명", "")
    elif "방문자" in message:
        messageType = "event"

    print(messageType, infectedCount)
    return messageType, int(infectedCount)

if __name__ == '__main__':
    type_detector(sys.argv[1])