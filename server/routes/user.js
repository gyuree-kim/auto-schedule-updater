var express = require('express');
var router = express.Router();

// models
const User = require('../models/user');
const ChatRooms = require('../models/chatRooms');
const Appointments = require('../models/appointments');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('hello user!');
});


// get all users
router.get('/', (req, res) => {
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
    if(err) { return res.status(400).json({msg:'newUser.save 실패'}); }
    else
    {
      // Save a chat room list for the new user 
      chatRooms.save((err)=>{
        if(err) { return res.status(400).json({msg:'chatRooms.save 실패'}); }
        else
        {
          appointments.save((err,appointment)=>{
            if(err) { return res.status(400).json({msg:'appointments.save 실패'}); }
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
router.get('/search/:id', (req, res) => {
  User.findOne({id: req.params.id}, (err, user) => {
    if(err) { res.status(400).json({error: `user findOne error`}); }
    if(!user) { res.status(404).json({msg: `user not found`}); }
    else
    {
      console.log('user findOne by id 성공');
      return res.status(200).json(user);
    }
  })
})

// search user by _id
router.get('/search/:_id', (req, res) => {
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
router.get('/search/:name', (req, res) => {
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
router.put('/update/:userId', function(req, res){
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

module.exports = router;