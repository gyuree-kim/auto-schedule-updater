const express = require('express');
const router = express.Router();

const main = require('./main');
const user = require('./user');
const message = require('./message');
const chatRoom = require('./chatRoom');
const appointment = require('./appointment');

router.use('/main', main);
router.use('/user', user);
router.use('/message', message);
router.use('/chatRoom', chatRoom);
router.use('/appointment', appointment);

module.exports = router;