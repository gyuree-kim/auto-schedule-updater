

// type_detector.py test
const type_detector_result = spawn('python', ['sms_ner_pkg/sms_ner_pkg/type_detector.py', '신규 확진자 1명 발생']); 
type_detector_result.stdout.on('data', function(result) { 
    console.log(result.toString()); 
})
type_detector_result.stderr.on('data', function(err) { 
    console.log(err.toString()); 
});

// package main 파일 실행 결과
const message_content = '신규 확진자 1명 발생'
const ner_package_result = spawn('python', ['sms_ner_pkg/sms_ner_pkg/main.py', message_content]); 
ner_package_result.stdout.on('data', function(result) { 
    console.log(result.toString()); 
})
ner_package_result.stderr.on('data', function(err) { 
    console.log(err.toString()); 
});
