var express = require('express');
var router = express.Router();

// models
const User = require('../models/user');
const Message = require('../models/message');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('hello message!');
});

// create message
router.post('/create', function(req, res){
  const message = new Message({
    chatRoomId: req.body.chatRoomId,
    sender: req.body.sender,
    content: req.body.content,
    createdAt: new Date
  })
  message.save((err) => {
    if(err)
    {
      console.log('message.save error');
      return res.state(400).json({msg: 'message save error'});
    }
    else
    {
      console.log('message.save 성공');
      return res.status(201).json({msg: 'message save 성공'});
    }
  })
});

// remove message by id
router.delete('/remove/:messageId', function(req, res){
  message.findOneAndDelete({
    _id: req.params.messageId
  }, function(err){
    if(err) 
    {
      console.log('message.remove error');
      return res.state(400).json({msg: 'message.remove error'});
    }
    else
    {
      console.log('message.remove 성공');
      return res.state(400).json({msg: 'message remove 성공'});
    }
  })
});

module.exports = router;