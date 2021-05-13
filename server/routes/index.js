const express = require('express');
const router = express.Router();

const main = require('./main');
const user = require('./user');
const message = require('./message');
const chatRoom = require('./chatRoom');
const appointment = require('./appointment');

router.use('/main', main);
router.use('/users', user);
router.use('/messages', message);
router.use('/chatRooms', chatRoom);
router.use('/appointments', appointment);

module.exports = router;