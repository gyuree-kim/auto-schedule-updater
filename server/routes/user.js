var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const mongoClient = require('mongodb').MongoClient

// models
const User = require('../models/user');
const ChatRooms = require('../models/chatRooms');
const Appointments = require('../models/appointments');

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
router.post('/register/old', (req, res)=>{
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

//youtube example
router.post('/register', (req, res) => {
  const newUser = new User({
    id: req.body.id,
    name: req.body.name,
    password: req.body.password,
    color: "#cecece",
    createdAt: new Date
  });
  const query = { id: newUser.id }
  User.findOne(query, (err, result) => {
      if(result) 
      {
        res.send(`already exist ${result}`);
        res.status(400);
      } 
      else 
      {
        newUser.save((err) => {
          if(err){ throw err; } 
          else {
            res.send(`success ${newUser}`);
            res.status(200);
          }
        })
      }
  })
})

router.post('/login', (req, res) => {
  const query1 = { id: req.body.id }
  const query2 = {
      id: req.body.id, 
      password: req.body.password
  }
  User.findOne(query1, (err, user) => {
    if(err){ res.status(400).json("fail to login")}
    else if (!user)
    {
      console.log("not registerd");
      res.status(404).json("not registered")
    }
    else
    {
      User.findOne(query2, (err, result) => {
        if(err){ res.status(400).json("fail to login")}
        else if(!result)
        {
          console.log("invalid password");
          res.status(404);
        }
        else 
        {
          const objToSend = {
              name: result.name,
              id: result.id
          }
          res.status(201).send(JSON.stringify(objToSend))
        } 
      })
    }
  })
})

// search user by id
router.get('/id/:id', (req, res) => {
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
router.get('/_id/:_id', (req, res) => {
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
router.get('/name/:name', (req, res) => {
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
router.put('/:userId', function(req, res){
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

// remove user
router.delete('/:_id', function(req, res){
  User.find({id: req.params._id})
    .remove( {}, function(err, user){  
      if(err) res.status(400);
      else 
      {
        res.send(`delete success ${user}`)
        res.status(200);
      }
  })
})

module.exports = router;