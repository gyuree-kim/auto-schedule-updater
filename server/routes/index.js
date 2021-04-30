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

router.get('/', function(req, res) {
    res.send('Hello world!');
});

// user register
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
  newUser.save((err)=>{
    if(err)
    {
      console.log("Registration Error")
      console.error(err);
      return res.status(400).json({msg:'newUser.save 실패'});
    }
    else
    {
      // Save a chat room list for the new user 
      chatRooms.save((err)=>{
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

// get all users
router.get('/user/getAll', (req, res) => {
  const filter = {}; //find all users
  User.find(filter, (err, userList)=>{
    if(err) res.status(404).json({msg: `users not found`});
    else
    {
      console.log('find userList 성공');
      return res.status(200).json({userList});
    }
  })
})

// search user by id
router.get('/user/get/:id', (req, res) => {
  User.findOne({id: req.params.id}, (err, user) => {
    if(err) res.status(500).json({error: `user findOne error`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by id 성공');
      return res.status(200).json(user);
    }
  })
})

// search user by _id
router.get('/user/get/:_id', (req, res) => {
  User.findOne({_id: req.params._id}, (err, user) => {
    if(err) res.status(500).json({error: `user findOne error`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by _id 성공');
      return res.status(200).json(user);
    }
  })
})

// search user by name
router.get('/user/get/:name', (req, res) => {
  User.findOne({name: req.params.name}, (err, user) => {
    if(err) res.status(500).json({error: `user findOne error`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by name 성공');
      return res.status(200).json(user);
    }
  })
})

// update user by id
router.put('/user/update/:userId', function(req, res){
  User.findById(req.params.userId, function(err, user){
    if(err) return res.status(500).json({ error: 'database failure' } );
    if(!user) return res.status(404).json({ error: 'user not found' });

    if(req.body._id) user._id = req.body._id;
    if(req.body.name) user.name = req.body.name;
    if(req.body.password) user.password = req.body.password;
    if(req.body.createdAt) user.createdAt = req.body.createdAt;

    user.save(function(err){
      if(err) return res.state(500).json({error: 'user update 실패'});
      else res.json({message: 'user update 성공'});
    });
  })
})

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