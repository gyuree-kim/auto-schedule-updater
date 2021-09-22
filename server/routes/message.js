var express = require('express');
var router = express.Router();
const User = require('../models/user');
const Message = require('../models/message');
const eventRouter = require('./event');
var pythonPackage = require('../python_pacakge')

const packageResult = pythonPackage.run_main()

/// create message -ok
router.post('/', function(req, res) {
  try {
    const userId = req.body.userId;
    const content = req.body.content;
    
    const _data = [userId, content]
    if(!_data) res.status(400).send("invalid input")

    // check if user exist
    User.findOne({userId:userId}, (err, user) => {
      if(err) throw err;
      if(!user) return res.status(404).send("user not found");
    });

    // create message
    const message = new Message({
      userId: userId,
      content: content,
      createdAt: new Date
    })
    message.save((err) => {
      if(err) throw err;
      else return res.status(201).send(message);
    })

    // 파이썬 패키지 결과
    console.log(packageResult)
    // const type = packageResult.filter(val => val[0] == 'type')[0][1]
    // const count = packageResult.filter(val => val[0] == 'count')[0][1]
    // const date = packageResult.filter(val => val[0] == 'date')[0][1]
    // const time = packageResult.filter(val => val[0] == 'time')[0][1]
    // console.log([type, count, date, time])

    // eventRouter.post()
    // 이벤트 저장
    // const event = new Event({
    //   messageId: message._id,
    //   type: type,
    //   count: count,
    //   date: date,
    //   time: time,
    //   location: '',
    //   createdAt: new Date,
    //   updatedAt: new Date
    // })
    // event.save((err) => {
    //     if(err) throw new Error()
    //     else return res.status(201).send(event)
    // })

  } catch(e) {
    console.log(err)
    res.status(500).send(e);
  }
});

/// get all messages -ok
router.get('/', (req, res) => {
  try {
    async function getMessage(){
      const message = await Message.find({});
      if(!message.length) {
        return res.status(404).send("message not found");
      } else {
        res.status(200).send(message);
      }
    }
    getMessage();
  } catch(e){
    res.status(500).send(e);
  }
})

/// get a message by messageId -ok
router.get('/messageId/:messageId', (req, res) => {
  const filter = {_id: req.params.messageId};
  Message.findOne(filter, (err, result) => {
    if(err) res.status(400).json({msg: `db error`});
    if(!result) res.status(404).json({msg: `message not found`});
    else return res.status(200).send(`success ${result}`);
  })
})

/// get messages by userId -ok
router.get('/userId/:userId', (req, res) => {
  const filter = {userId: req.params.userId};
  Message.find(filter, (err, result) => {
    if(err) res.status(400).json({msg: `db error`});
    if(!result) res.status(404).json({msg: `message not found`});
    else return res.status(200).send(result);
  })
})

/// remove message by id -ok
router.delete('/messageId/:messageId', function(req, res){
  const filter = {_id: req.params.messageId };
  Message.findOne(filter, (err, message) => {
    if(!message) res.status(404).send("msg not found");
    else
    {
      message.remove( filter, function(err, message){
        if(err) return res.status(400).json({msg: 'message.remove error'});
        else return res.status(200).send(message);
      })
    }
  })
});

module.exports = router;