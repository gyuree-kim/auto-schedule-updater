
// single pyhon file test
const spawn = require('child_process').spawn; 
const result = spawn('python', ['python_test.py']); 
result.stdout.on('data', function(data) { 
    console.log(data.toString()); 
}); 
result.stderr.on('data', function(data) { 
    console.log(data.toString()); 
});

// type_detector.py test
const type_detector_result = spawn('python', ['sms_ner_pkg/sms_ner_pkg/type_detector.py', '신규 확진자 1명 발생']); 
type_detector_result.stdout.on('data', function(result) { 
    console.log(result.toString()); 
})
type_detector_result.stderr.on('data', function(err) { 
    console.log(err.toString()); 
});