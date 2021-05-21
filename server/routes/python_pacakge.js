var express = require('express');
var router = express.Router();
import {PythonShell} from 'python-shell';

router.get('/', (req, res) => {
    content = req.body.content;

    let options = {
        mode: 'text',
        pythonPath: 'C:/Users/ghio1/AppData/Roaming/Python/Python38/Scripts',
        pythonOptions: ['-u'], // get print results in real-time
        scriptPath: '../',
        args: ["content"]
    };
    PythonShell.run('python_test.py', options, function (err, result) {
        if (err) throw err;
        console.log(`result: ${result}`);
    });

    res.status(200).send(result);
})

module.exports = router;