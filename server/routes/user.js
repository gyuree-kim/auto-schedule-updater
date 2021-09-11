var express = require('express');
var router = express.Router();
const User = require('../models/user');

// get all users -ok
router.get('/', (req, res) => {
  const filter = {}; //find all users
  User.find(filter, (err, userList)=>{
    if(err) {
      console.log(err)
      res.status(400).json({msg: `getAll users error`})
    }
    if(!userList) res.status(404).json({msg: `users not found`});
    else
    {
      return res.status(200).send(userList);
    }
  })
})

// user register
// router.post('/register/old', (req, res)=>{
//   const newUser = new User({
//     name: req.body.name,
//     id: req.body.id,
//     password: req.body.password,
//     createdAt: new Date
//   })
//   const chatRooms = new ChatRooms({
//     userId: req.body.id,
//     updatedAt: new Date
//   })
//   const appointments = new Appointments({
//     userId: req.body.id,
//     updatedAt: new Date
//   })

//   // Save a new user
//   newUser.save((err)=>{
//     if(err) { return res.status(400).json({msg:'newUser.save 실패'}); }
//     else
//     {
//       // Save a chat room list for the new user 
//       chatRooms.save((err)=>{
//         if(err) { return res.status(400).json({msg:'chatRooms.save 실패'}); }
//         else
//         {
//           appointments.save((err,appointment)=>{
//             if(err) { return res.status(400).json({msg:'appointments.save 실패'}); }
//             else
//             {
//               console.log('New user is saved.');
//               return res.status(201).json({msg:'New user is saved.'});
//             }
//           })
//         }
//       })
//     }
//   });
// });

// register -ok
router.post('/register', (req, res) => {
  const newUser = new User({
    userId: req.body.userId,
    username: req.body.username,
    password: req.body.password,
    createdAt: new Date
  })

  const query = { userId: newUser.userId }
  User.findOne(query, (err, result) => {
      if(err) { 
        console.log(err)
        res.status(400).json("fail to register")
      }
      if(result) {
        res.send(`already exist ${result}`);
        res.status(400);
      } 
  })

  newUser.save((err) => {
    if(err) { 
      console.log(err)
      res.status(400).json("fail to save user") 
    }
    else { return res.status(201).send(newUser) }
  })
})

// login -ok
router.post('/login', (req, res) => {
  const query1 = { userId: req.body.userId }
  const query2 = {
    userId: req.body.userId, 
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
            username: result.username,
            userId: result.userId
          }
          res.status(201).send(JSON.stringify(objToSend))
        } 
      })
    }
  })
})

// search user by userId -ok
router.get('/userId/:userId', (req, res) => {
  User.findOne({userId: req.params.userId}, (err, user) => {
    if(err) { res.status(500).json({error: `db failure`}); }
    if(!user) { res.status(404).json({msg: `user not found`}); }
    else
    {
      console.log('user findOne by userId 성공');
      return res.status(200).send(user);
    }
  })
})

// search user by _id -ok
router.get('/_id/:_id', (req, res) => {
  User.findOne({_id: req.params._id}, (err, user) => {
    if(err) res.status(500).json({error: `db failure`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by _id 성공');
      return res.status(200).send(user);
    }
  })
})

// search user by username -ok
router.get('/username/:username', (req, res) => {
  User.findOne({name: req.params.name}, (err, user) => {
    if(err) res.status(500).json({error: `db failure`});
    if(!user) res.status(404).json({msg: `user not found`});
    else
    {
      console.log('user findOne by username 성공');
      return res.status(200).send(user);
    }
  })
})

// update user by userId
router.put('/:userId', function(req, res){
  User.findOne({ userId: req.params.userId }, function(err, user){
    if(err) res.status(400).json({ error: 'database failure' } );
    if(!user) res.status(404).json({ error: 'user not found' });

    if(req.body.userId) user.userId = req.body.userId;
    if(req.body.username) user.username = req.body.username;
    if(req.body.password) user.password = req.body.password;
    if(req.body.color) user.color = req.body.color;
    if(req.body.createdAt) user.createdAt = req.body.createdAt;

    user.save(function(err){
      if(err) res.state(500).json({error: 'database error'});
      else 
      {
        res.json({message: 'user update 성공'});
        res.status(200).send(user);
      }
    });
  })
})

// remove user
router.delete('/userId/:userId', function(req, res){
  User.findOne({userId: req.params.userId}, (err, user) =>{
    user.remove( {}, function(err, user){  
      if(err) res.status(500).json("db failure");
      else 
      {
        res.send(`delete success ${user}`)
        res.status(200);
      }
    })
  }) 
})

module.exports = router;
