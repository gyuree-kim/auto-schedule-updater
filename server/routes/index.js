const express = require('express');
const router = express.Router();

const main = require('./main');
const user = require('./user');
const message = require('./message');
const event = require('./event');

router.use('/main', main);
router.use('/users', user);
router.use('/messages', message);
router.use('/events', event);

module.exports = router;