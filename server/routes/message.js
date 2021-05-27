var express = require('express');
var router = express.Router();
const User = require('../models/user');
const Message = require('../models/message');

// create message 
router.post('/', function(req, res){
  try{
    const userId = req.body.userId;
    const content = req.body.content;
    const sentAt = req.body.sentAt;
    
    if(!content || !sentAt) res.status(400).send("both content and sentAt are required")

    async function findUser(){
      const user = await User.findOne({id:userId}, (err)=>{
        if(err) throw err;
      });
      if(!user) return res.status(404).send("user not found");
    }
    if(userId) findUser()

    const message = new Message({
      userId: userId,
      content: content,
      sentAt: sentAt
    })
    message.save((err) => {
      if(err) throw err;
      else return res.status(201).send(message);
    })
  } catch(e){
    res.status(500).send(e);
  }
});

// get all messages
router.get('/', (req, res) => {
  try{
    async function getMessage(){
      const message = await Message.find({});
      if(!message.length){
        return res.status(404).send("message not found");
      }
      else res.status(200).send(message);
    }
    getMessage();
  }catch(e){
    res.status(500).send(e);
  }
})

// get a message by messageId
router.get('/messageId/:messageId', (req, res) => {
  const filter = {_id: req.params.messageId};
  Message.findOne(filter, (err, result) => {
    if(err) res.status(400).json({msg: `db error`});
    if(!result) res.status(404).json({msg: `message not found`});
    else return res.status(200).send(`success ${result}`);
  })
})

// get messages by eventId  ??
router.get('/eventId/:eventId', (req, res) => {
  const filter = {content: req.params.eventId};
  Event.find(filter, (err, result)=>{
    if(err) res.status(400).json({msg: `db error`});
    if(!result) res.status(404).json({msg: `event not found`});
    else
    {
      return res.status(200).send(`success ${result}`);
    }
  })
})

// remove message by id 
router.delete('/:messageId', function(req, res){
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