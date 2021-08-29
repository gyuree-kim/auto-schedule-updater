var express = require('express');
var router = express.Router();
var request = require('request');
const User = require('../models/user');
const Event = require('../models/event');

// create new event
router.post('/', (req,res) => {
    try{
        const date = req.body.date;
        const time = req.body.time;
        const location = req.body.location;
        if(!date || !time || !location) {
          res.send('input is not sufficient');
          throw new Error()
        }

        const event = new Event({
            date: date,
            time: time,
            location: location,
            color: "#fff",
            createdAt: new Date,
            updatedAt: new Date
        })
        event.save((err) => {
            if(err) throw new Error()
            else return res.status(201).send(event)
        })
    } catch(e){
        res.status(500).send(e)
    }
})

// get all events
router.get('/', (req, res) => {
  try{
    async function getEvents(){
      const events = await Event.find({});
      if(!events.length) return res.status(404).send("event not found");
      else res.status(200).send(events);
    }
    getEvents();
  }catch(e){
    res.status(500).send(e);
  }
})

// get a event by id 
router.get('/eventId/:_id', (req, res) => {
  const filter = {_id: req.params._id};
  Event.findOne(filter, (err, result)=>{
    if(err) res.status(400).send(err)
    if(!result) res.status(404).json({msg: `event not found`});
    else return res.status(200).send(result);
  })
})

// get a event by userId 
router.get('/userId/:userId', (req, res) => {
  const filter = {id: req.params.userId};
  Event.find(filter, (err, result)=>{
    if(err) res.status(400).send(err)
    if(!result.length) res.status(404).json({msg: `user has no event`});
    else return res.status(200).send(result);
  })
})

// update event 
router.put('/eventId/:eventId', function(req, res){
  const filter = { _id: req.params.eventId };
  Event.findOne(filter, (err, result) => {
    if(err) return res.status(400).send(err);
    if(!result) return res.status(404).send('event not found');

    if(req.body.date) result.date = req.body.date;
    if(req.body.time) result.time = req.body.time;
    if(req.body.location) result.location = req.body.location;
    result.updatedAt = new Date;

    result.save(function(err){
        if(err) return res.status(500).send(err);
        else res.status(200).send(result);
    });
  })
})

// remove event by id
router.delete('/eventId/:eventId', function(req, res){
  const filter = {_id: req.params.eventId };
  Event.findOne(filter, (err, result) => {
    if(!result) res.status(404).send("event not found");
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