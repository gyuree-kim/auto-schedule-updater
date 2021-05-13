var express = require('express');
var router = express.Router();
var request = require('request');
const User = require('../models/user');
const Appointment = require('../models/appointments');
const ChatRoom = require('../models/chatRooms')

// create a new appointment - ok
router.post('/', (req,res) => {
    try{
        const userId = req.body.userId; //User.id
        const chatRoomId = req.body.chatRoomId;

        if(!userId) throw Error('username required');
        if(!chatRoomId) throw Error('chatRoomId required')
        async function getUser() {
            const user = await User.findOne({ id: userId });
            if(!user) return res.status(404).send('user not found')
            console.log("found user");
            return user
        }
        async function getChatRoom() {
            const room = await ChatRoom.findOne({ _id: chatRoomId });
            if(!room) return res.status(404).send('chatRoom not found')
            console.log("found chatRoom");
            return room
        }       
        getUser()
        getChatRoom()
        
        const appointment = new Appointment({
            userId: userId,
            chatRoomId: chatRoomId,
            date: req.body.date,
            time: req.body.time,
            location: req.body.location,
            createdAt: new Date,
            updatedAt: new Date
        })
        appointment.save((err) => {
            if(err) throw new Error()
            else return res.status(201).send(appointment)
        })
    }catch(e){
        res.status(500).send(e)
    }
})

// get all appointments - ok
router.get('/', (req, res) => {
  try{
    async function getAppointments(){
      const appointments = await Appointment.find({});
      if(!appointments.length){
        return res.status(404).send("appointment not found");
      }
      else res.status(200).send(appointments);
    }
    getAppointments();
  }catch(e){
    res.status(500).send(e);
  }
})

// get a appointment by id - ok
router.get('/_id/:_id', (req, res) => {
  const filter = {_id: req.params._id};
  Appointment.findOne(filter, (err, result)=>{
    if(err) res.status(400).send(err)
    if(!result) res.status(404).json({msg: `appointment not found`});
    else
    {
      return res.status(200).send(result);
    }
  })
})

// get appointments by username - ok
router.get('/username/:username', (req, res) => {
  const filter = {username: req.params.username};
  Appointment.find(filter, (err, result)=>{
    if(err) res.status(400).send(err)
    if(!result) res.status(404).json({msg: `appointment not found`});
    else
    {
      return res.status(200).send(result);
    }
  })
})

// update appointment - ok
router.put('/_id/:_id', function(req, res){
  const filter = { _id: req.params._id };
  Appointment.findOne(filter, function(err, result){
    if(err) res.status(500).send(err);
    if(!result) res.status(404).send( 'appointment not found' );

    if(req.body.date) result.date = req.body.date;
    if(req.body.time) result.time = req.body.time;
    if(req.body.location) result.location = req.body.location;

    result.save(function(err){
        result.updatedAt = new Date
        if(err) res.status(500).send(err);
        else 
        {
            res.status(200).send(result);
        }
    });
  })
})

// remove appointment by id - ok
router.delete('/_id/:_id', function(req, res){
  const filter = {_id: req.params._id };
  Appointment.findOne(filter, (err, result) => {
    if(!result) res.status(404).send("appointment not found");
    else
    {
      result.remove( {}, function(err, result){
        if(err) return res.status(400).send(err)
        else return res.status(200).send(result);
      })
    }
  })
});
module.exports = router;