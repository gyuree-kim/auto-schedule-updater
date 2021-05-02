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

// api list
router.get("/", function (req, res, next) {
  res.render("list", {
    title: "api list",
    apilist: [
      // user
      {
        name: `${req.headers.host}/api/users`,
        description: "get all users",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/login`,
        description: "user authentication",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/user/register`,
        description: "register new user",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/user/search/:id`,
        description: "search user by id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/search/:_id`,
        description: "search user by _id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/search/:name`,
        description: "search user by name",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/update/:userId`,
        description: "update user by userId",
        method: "put",
      },
      // message
      {
        name: `${req.headers.host}/api/messages/:chatRoomId`,
        description: "get all messages by chatRoomId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/message/:content`,
        description: "get message with content",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/message/create`,
        description: "create new message",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/message/remove/:messageId`,
        description: "remove message by messageId",
        method: "delete",
      },
      // appointment
      {
        name: `${req.headers.host}/api/appointment/create`,
        description: "create a new appointment",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/appointment/remove/:id`,
        description: "remove appointment by id",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/appointment/update/:id`,
        description: "update appointment by id",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/appointments/:userId`,
        description: "get all appointments by userId",
        method: "get",
      },
      // chatroom
      {
        name: `${req.headers.host}/api/chatRoom/create/:userId`,
        description: "create new chatRoom with userId",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/chatRoom/search/:userId`,
        description: "search chatRoom by userId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/chatRoom/remove/:chatRoomId`,
        description: "remove chatRoom by chatRoomId",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/chatRoom/update/:chatRoomId`,
        description: "update chatRoom by chatRoomId",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/chatRooms/:userId`,
        description: "get all chatRooms by userId",
        method: "get",
      },
    ],
  });
});


router.get('/', function(req, res) {
    res.send('Hello world!');
});

// get all users
router.get('/user', (req, res) => {
  const filter = {}; //find all users
  User.find(filter, (err, userList)=>{
    if(err) res.status(400).json({msg: `getAll users error`});
    if(!userList) res.status(404).json({msg: `users not found`});
    else
    {
      console.log('find userList 성공');
      return res.status(200).json({userList});
    }
  })
})

// user register
router.post('user/register', (req, res)=>{
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
          return res.status(400).json({msg:'chatRooms.save 실패'});
        }
        else
        {
          appointments.save((err,appointment)=>{
            if(err)
            {
              console.error(err);
              return res.status(400).json({msg:'appointments.save 실패'});
            }
            else
            {
              console.log('New user is saved.');
              return res.status(201).json({msg:'New user is saved.'});
            }
          })
        }
      })
    }
  });
});

// search user by id
router.get('/user/search/:id', (req, res) => {
  User.findOne({id: req.params.id}, (err, user) => {
    if(err) res.status(400).json({error: `user findOne error`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by id 성공');
      return res.status(200).json(user);
    }
  })
})

// search user by _id
router.get('/user/search/:_id', (req, res) => {
  User.findOne({_id: req.params._id}, (err, user) => {
    if(err) res.status(400).json({error: `user findOne error`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by _id 성공');
      return res.status(200).json(user);
    }
  })
})

// search user by name
router.get('/user/search/:name', (req, res) => {
  User.findOne({name: req.params.name}, (err, user) => {
    if(err) res.status(400).json({error: `user findOne error`});
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
    if(err) return res.status(400).json({ error: 'database failure' } );
    if(!user) return res.status(404).json({ error: 'user not found' });

    if(req.body._id) user._id = req.body._id;
    if(req.body.name) user.name = req.body.name;
    if(req.body.password) user.password = req.body.password;
    if(req.body.createdAt) user.createdAt = req.body.createdAt;

    user.save(function(err){
      if(err) return res.state(400).json({error: 'user update 실패'});
      else 
      {
        res.json({message: 'user update 성공'});
        return res.status(200).json(user);
      }
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
      return res.status(201).json({msg: 'message save 성공'});
    }
  })
});

// remove message by id
router.delete('/message/remove/:messageId', function(req, res){
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