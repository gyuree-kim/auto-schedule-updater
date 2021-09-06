const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const User = require('./user');
const Event = require('./event');

const message = new Schema({
    userId: { type: String, ref: 'User' },
    content: { type: String, required: true },
    createdAt: { type: Date, required: true }
});

module.exports = mongoose.model('message', message);