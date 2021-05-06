var express = require('express');
var router = express.Router();

const ChatRooms = require('../models/chatRooms');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('hello chatRoom!');
});

module.exports = router;