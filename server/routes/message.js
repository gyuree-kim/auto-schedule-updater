var express = require('express');
var router = express.Router();

const User = require('../models/user');
const Message = require('../models/message');
const ChatRoom = require('../models/chatRooms');

// create message
router.post('/', function(req, res){
  const chatRoomId = req.body.chatRoomId;
  const sender = req.body.sender;

  async function findChatRoom(){
    const chatRoom = await ChatRoom.findOne({_id:chatRoomId});
    if(!chatRoom) return res.status(404).send("chatroom not found");
    console.log("found chatroom");
  }
  async function findUser(){
    const user = await ChatRoom.findOne({id:sender});
    if(!user) return res.status(404).send("user not found");
    console.log("found user");
  }

  async function createMessage(message){
    findChatRoom()
    findUser()
    message.save((err) => {
      if(err) return res.status(500).send(err);
      else return res.status(201).send(message);
    })
  }
  try{
    const message = new Message({
      chatRoomId: chatRoomId,
      sender: sender,
      content: req.body.content,
      isRead: false,
      createdAt: new Date
    })
    createMessage(message)

  } catch(e){
    res.status(500).send(e);
  }
});

// get all messages - ok
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

// get a message by messageId - ok
router.get('/_id/:messageId', (req, res) => {
  const filter = {_id: req.params.messageId};
  Message.findOne(filter, (err, result)=>{
    if(err) res.status(400).json({msg: `db error`});
    if(!result) res.status(404).json({msg: `message not found`});
    else
    {
      return res.status(200).send(`success ${result}`);
    }
  })
})

// get messages by chatRoomId - ok
router.get('/chatRoomId/:chatRoomId', (req, res) => {
  const filter = {chatRoomId: req.params.chatRoomId};
  Message.find(filter, (err, result)=>{
    if(err) res.status(400).send(err);
    if(!result.length) res.status(404).send("message not found");
    else
    {
      return res.status(200).send(`success ${result}`);
    }
  })
})

// get messages with content - ok
router.get('/content', (req, res) => {
  const filter = {content: req.body.content};
  Message.find(filter, (err, result)=>{
    if(err) res.status(400).json({msg: `db error`});
    if(!result) res.status(404).json({msg: `message not found`});
    else
    {
      return res.status(200).send(`success ${result}`);
    }
  })
})

// update message - ok
router.put('/:messageId', function(req, res){
  const filter = { _id: req.params.messageId };
  Message.findOne(filter, function(err, message){
    if(err) res.status(500).send(err);
    if(!message) res.status(404).send( 'message not found' );

    if(req.body.isRead) message.isRead = req.body.isRead;

    message.save(function(err){
      if(err) res.status(500).send(err);
      else 
      {
        res.status(200).send(message);
      }
    });
  })
})

// remove message by id - ok
router.delete('/:messageId', function(req, res){
  const filter = {_id: req.params.messageId };
  Message.findOne(filter, (err, message) => {
    if(!message) res.status(404).send("msg not found");
    else
    {
      message.remove( {}, function(err, message){
        if(err) return res.status(400).json({msg: 'message.remove error'});
        else return res.status(200).send(message);
      })
    }
  })
});

module.exports = router;