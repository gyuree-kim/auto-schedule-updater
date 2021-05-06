var express = require('express');
var router = express.Router();

// models
const User = require('../models/user');
const Appointments = require('../models/appointments');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('hello appointment!');
});

module.exports = router;