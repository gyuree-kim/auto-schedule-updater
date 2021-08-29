const { PythonShell } = require('python-shell')

var result = []
function run_python_script(message) {
    // 파이썬 쉘 실행
    let options = {
        mode: 'text',
        pythonPath: 'python',
        pythonOptions: ['-u'], // get print results in real-time
        args: [message]
    };
    // filepath = "../../korean-nlp-package/sms_ner_pkg/main.py"
    var filepath = './python_test.py'
    let pyshell = new PythonShell(filepath, options)

    // // 파이썬 스크립트에 데이터 전달
    // pyshell.send('hello').end(function(err){
    //     if (err) throw err;
    // })

    // 파이썬 스크립트로부터 출력(print)된 결과값 받기
    pyshell.on('data', (data) => { 
        console.log(data)
        return data
    })

    // 파이썬 스크립트 프로세스 종료
    pyshell.end((err) => {
        if (err) throw err;
        console.log('finished');    
    });    
}

run_python_script('메세지 샘플')