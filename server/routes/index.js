var mongodb = require('mongodb');
var express = require('express');
var bodyParser = require('body-parser');
var router = express.Router();
var mongoClient = mongodb.MongoClient;
var ObjectID = mongodb.ObjectID; 

// models
const User = require('../models/user');
const ChatRooms = require('../models/chatRooms');
const Message = require('../models/message');
const Appointments = require('../models/appointments');

// localhost:3000/api
router.get('/', function(req, res) {
    res.send('Hello world!');
});

// localhost:3000/api/register
router.post('/register', (req, res)=>{
  const newUser = new User({
    name: req.body.name,
    id: req.body.id,
    password: req.body.password,
    createdAt: new Date
  })
  const chatRooms = new ChatRooms({
    userId: req.body.id,
    updatedAt: new Date
  })
  const appointments = new Appointments({
    userId: req.body.id,
    updatedAt: new Date
  })

  // Save a new user
  newUser.save((err, user)=>{
    if(err)
    {
      console.log("Registration Error")
      console.error(err);
      return res.status(400).json({msg:'newUser.save 실패'});
    }
    else
    {
      // Save a chat room list for the new user 
      chatRooms.save((err, chatRoom)=>{
        if(err)
        {
          console.error(err);
          return res.status(404).json({msg:'chatRooms.save 실패'});
        }
        else
        {
          appointments.save((err,appointment)=>{
            if(err)
            {
              console.error(err);
              return res.status(404).json({msg:'appointments.save 실패'});
            }
            else
            {
              console.log('New user is saved.');
              return res.status(200).json({msg:'New user is saved.'});
            }
          })
        }
      })
    }
  });
});

// create message
router.post('/message/create', function(req, res){
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
      return res.status(200).json({msg: 'message save 성공'});
    }
  })
});

// remove message by id
router.delete('/message/delete/:messageId', function(req, res){
  message.findOneAndDelete({
    _id: req.params.messageId
  }, function(err){
    if(err) console.log('message.remove error');
    else
    {
      console.log('message.remove 성공');
      return res.state(400).json({msg: 'message remove 성공'});
    }
  })
});


module.exports = router;