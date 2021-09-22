
// package main 파일 실행 결과
function run_main() {
    const spawn = require('child_process').spawn;
    const message_content = '[서산시청] 5월 27일(목) 19시~21시까지 서산중앙병원 장례식장 별실 방문자는 6월 1일까지 보건소 선별진료소(09~18시까지)에서 검사 바랍니다.'		
    
    const ner_package_result = spawn('python', ['sms_ner_pkg/sms_ner_pkg/main.py', message_content]); 
    ner_package_result.stdout.on('data', function(result) { 
        console.log(result.toString()); 
    })
    ner_package_result.stderr.on('data', function(err) { 
        console.log(err.toString()); 
    });    
}

module.exports.run_main = run_main;
