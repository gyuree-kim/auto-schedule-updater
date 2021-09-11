var express = require('express');
var router = express.Router();
var request = require('request');
const User = require('../models/user');
const Message = require('../models/message');
const Event = require('../models/event');

// create new event
router.post('/', function(req, res) {
    try {
      const messageId = req.body.messageId
      const type = req.body.type
      const count = req.body.count
      const date = req.body.date
      const time = req.body.time
      const location = req.body.location

      // input 확인
      const commonParams = [messageId, type, location]
      const countParams = [count]
      const eventParams = [date, time]
      if(!commonParams) {
        return res.status(400).send("input is not sufficient");
      }

      // messageId 유효성 검사
      Message.findOne({ _id: messageId }, (err, message) => {
        if(err) {
          res.status(400).send(err)
        }
        if(!message) {
          res.status(404).json({msg: `no message with given messageId`})
        }
      })

      Event.findOne({ messageId: messageId }, (err, event) => {
        if (err) {
          res.status(400).send(err)
        }
        if(event) {
          res.status(400).send("already saved")
        }
      })

      // params 검사
      if (type == "count") {
        if (!countParams) {
          res.status(400).send("empty count")
        }
      } else if (type == "event") {
        if (!eventParams) {
          res.status(400).send("empty date or time")
        }
      } else { 
        throw new Error() 
      }

      // 인스턴스 생성
      const event = new Event({
        messageId: messageId,
        type: type,
        count: count,
        date: date,
        time: time,
        location: location,
        createdAt: new Date,
        updatedAt: new Date
      })

      // 저장
      event.save((err) => {
          if(err) throw new Error()
          else return res.status(201).send(event)
      })
    } catch(e){
        res.status(500).send(e)
    }
})

// get all events -ok
router.get('/', (req, res) => {
  try {
    async function getEvents(){
      const events = await Event.find({});
      if(!events.length) return res.status(404).send("event not found");
      else res.status(200).send(events);
    }
    getEvents();
  } catch(e) {
    res.status(500).send(e);
  }
})

// get a event by id -ok
router.get('/eventId/:_id', (req, res) => {
  const filter = {_id: req.params._id};
  Event.findOne(filter, (err, result) => {
    if(err) res.status(400).send(err)
    if(!result) res.status(404).json({msg: `event not found`});
    else return res.status(200).send(result);
  })
})

// get events by userId
router.get('/userId/:userId', (req, res) => {
  const filter = {userId: req.params.userId};
  Message.find(filter, (err, messages) => {
    if(err) res.status(400).send(err)
    if(!messages.length) res.status(404).json({msg: `no messages with given userId`});
    
    var _events = []
    messages.forEach( message => {
      const _filter = { messageId: message._id }
      Event.find(_filter, (err, event) => {
        if(err) res.status(400).send(err)
        if(!event.length) res.status(404).json({msg: `no event with messageId ${message._id}`})
        else _events.concat(event)
        console.log(_events.length)
      })
    });
    console.log(_events.length)
    return res.status(200).send(_events);
  })
})

// remove event by id -ok
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

// remove all events -ok
router.delete('/', function(req, res) {
  Event.deleteMany({}, (err) => {
    if(err) { res.status(400) }
    else { res.status(200).send("deleted all events") }
  })
});

module.exports = router;