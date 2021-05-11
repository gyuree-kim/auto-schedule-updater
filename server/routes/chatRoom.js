var express = require('express');
var router = express.Router();

const ChatRoom = require('../models/chatRooms');
const User = require('../models/user');

//create chatroom - ok
router.post('/', (req, res) => {
  try{
    const userId = req.body.userId;
    const invitedIds = req.body.invitedUsers;

    getUser();
    findUsers();
    
    async function getUser() 
    {
      const user = await User.findOne({ id: userId });
      if(!user) {
        throw new Error(`user not found`);
        return res.status(404)
      }
      return user;
    }

    function delay(id)
    {
      return new Promise(resolve => {
        setTimeout(() => {
          User.findOne({id:id}, (err, user)=>{
            if(err) return res.status(500)
            if(!user) return res.status(404).send("user not found");
            else resolve(user);
          })
        }, 500);
      });
    }

    async function findUsers() 
    {
      const promises = invitedIds.map(id => delay(id));
      await Promise.all(promises);
      console.log("found all users!");
      createChatRoom();
    }

    function createChatRoom() 
    {
      invitedIds.push(userId[0])
      const chatRoom = new ChatRoom({
        users: invitedIds,
        createdAt: new Date
      })
      chatRoom.save((err) => {
        if(err) return res.status(500).send("error")
        else
        {
          res.status(201).send(`success save ${chatRoom}`)
        }
      })
    }
  } catch (e) {
    res.status(500).send(e);
  }
});

// get all chatRooms - ok
router.get('/', (req, res) => {
  const filter = {};
  ChatRoom.find(filter, (err, chatRooms)=>{
    if(err) res.status(500).json({status:500, msg: `db error`});
    if(chatRooms == '') res.status(404).json({status:404, msg: `chatRoom not found`});
    else
    {
      return res.status(200).send(`success find chatRooms ${chatRooms}`);
    }
  })
})

// get all chatRooms by user id - ok
router.get('/:userId', (req, res) => {
  const filter = { 'users': { $in: [req.params.userId] } };
  ChatRoom.find(filter, (err, chatRooms)=>{
    if(err) res.status(500).json({status:500, msg: `db error`});
    if(chatRooms == '') res.status(404).json({status:404, msg: `chatRoom not found`});
    else
    {
      return res.status(200).send(`success find chatRooms ${chatRooms}`);
    }
  })
})

//get recent msg data - ok
router.get('/:chatRoomId/recent-message', (req, res) => {
  const filter = { _id: req.params.chatRoomId };
  ChatRoom.findOne(filter, (err, chatRoom)=>{
    if(err) res.status(500).json({msg: `db error`});
    if(!chatRoom) res.status(404).json({msg: `chatRoom not found`});
    if(!chatRoom.recentMsg) res.status(404).send(`message not found`);
    else
    {
      return res.status(200).send(`success find chatRoom ${chatRoom.recentMsg}`);
    }
  })
})

// update chatRoom by _id
router.put('/:chatRoomId', function(req, res){
  const filter = { _id: req.params.chatRoomId };
  ChatRoom.findOne(filter, function(err, chatRoom){
    if(err) res.status(500).json({ msg: 'db failure' } );
    if(!chatRoom) res.status(404).send( 'chatRoom not found' );

    if(req.body.recentMsg) chatRoom.recentMsg = req.body.recentMsg;

    chatRoom.save(function(err){
      if(err) res.status(500).send({error: 'database error'});
      else 
      {
        res.status(200).send(`status: 200, chatRoom: ${chatRoom}`);
      }
    });
  })
})

// remove chatRoom - ok
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