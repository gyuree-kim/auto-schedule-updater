const { debugPort } = require('process');
const { once } = require('events'); 

// package main 파일 실행 결과
function run_main() {
    // data
    const message_content = '[서산시청] 5월 27일(목) 19시~21시까지 서산중앙병원 장례식장 별실 방문자는 6월 1일까지 보건소 선별진료소(09~18시까지)에서 검사 바랍니다.'		
    const file_path = 'sms_ner_pkg/sms_ner_pkg/main.py'
    var result 

    // processes
    const spawn = require('child_process').spawn;
    var exec = require('child_process').exec;
    const process = spawn('python', [file_path, message_content]); 

    // process.stdin.resume()
    // process.stdin.on('data', function(data) {
    //     try {
    //         process.stdout.write(data+'\n')
    //     } catch(err) {
    //         process.stdout.write(err.message+'\n')
    //     }
    // })

    /// exec
    // var obj = {};
    // obj.list = function(callback) {
    //     exec("ls -al", function (err, stdout, stderr) {
    //         if(err) {
    //             console.log(err.toString()); 
    //         }
    //         callback(stdout);
    //     });
    // }
    // obj.list(function(stdout) {
    //     console.log('Ta da : '+ stdout);
    // });

    process.stdout.on('data', function(data) { 
        console.log(`RUN MAIN RESULT: ${data.toString()}`)
        // process.stdin.write(data+'\n')
    })
    process.stderr.on('data', function(err) { 
        console.log(err.toString()); 
    });

    return result
}

// main.py 비동기 실행
async function async_run_main(message) {
    // data
    const file_path = 'sms_ner_pkg/sms_ner_pkg/main.py'
    var result = ''

    // process
    var spawn = require('child_process').spawn
    var proecess = spawn('python', [file_path, message])
    proecess.stdin.setEncoding = 'utf-8';

    proecess.stdout.on('data', (data) => {
        result += data.toString();
        console.log(result);
    });
    proecess.stderr.on('data', (data) => {
        console.log('error:' + data);
    });
    proecess.stdout.on('end', async function(code){
        console.log('result: ' + result);
        console.log(`Exit code is: ${code}`);
    });

    await once(proecess, 'close')
    return result;
}

module.exports.run_main = run_main;
module.exports.async_run_main = async_run_main;
