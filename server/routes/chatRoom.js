var express = require('express');
var router = express.Router();

const ChatRoom = require('../models/chatRooms');
const User = require('../models/user');

//create chatroom
router.post('/', (req, res) => {
  const users = req.body.users; //user.id
  const userIds = []; //user._id
  for(var i = 0; i < users.length; i++) {
    User.findOne({id: users[i]}, (err, user) => {
      if(err) return res.status(500).send('db error')
      else
      {
        userIds[i] = user._id
      }
    })
  }
  const chatRoom = new ChatRoom({
    users: userIds,
    createdAt: new Date
  })
  chatRoom.save((err) => {
    if(err) res.status(500).send("db error")
    else{
      res.status(201).send(`success save ${chatRoom}`)
    }
  })
})

// get all chatRooms by user id
router.get('/:userId', (req, res) => {
  const filter = { id: req.params.userId };
  ChatRoom.find(filter, (err, chatRooms)=>{
    if(err) res.status(500).json({msg: `db error`});
    if(!chatRooms) res.status(404).json({msg: `chatRoom not found`});
    else
    {
      return res.status(200).send(`success find chatRooms ${chatRooms}`);
    }
  })
})

//get recent msg data
router.get('/:chatRoomId/recent-message', (req, res) => {
  const filter = { id: req.params.chatRoomId };
  ChatRoom.findOne(filter, (err, chatRoom)=>{
    if(err) res.status(500).json({msg: `db error`});
    if(!chatRoom) res.status(404).json({msg: `chatRoom not found`});
    else
    {
      return res.status(200).send(`success find chatRoom ${chatRoom.recentMsg}`);
    }
  })
})

// update chatRoom by _id
router.put('/:_id', function(req, res){
  ChatRoom.findOne({ _id: req.params._id }, function(err, chatRoom){
    if(err) res.status(500).json({ error: 'database failure' } );
    if(!chatRoom) res.status(404).json({ error: 'chatRoom not found' });

    if(req.body.recentMsg) chatRoom.recentMsg = req.body.recentMsg;

    chatRoom.save(function(err){
      if(err) res.state(500).json({error: 'database error'});
      else 
      {
        res.json({message: 'chatRoom update 성공'});
        res.status(200).json(chatRoom);
      }
    });
  })
})

// remove chatRoom
router.delete('/_id/:_id', function(req, res){
  ChatRoom.findOne({_id: req.params._id}, (err, result) =>{
    result.remove( {}, function(err, result){  
      if(err) res.status(500).json("db failure");
      else 
      {
        res.send(`delete success ${result}`)
        res.status(200);
      }
    })
  }) 
})
module.exports = router;