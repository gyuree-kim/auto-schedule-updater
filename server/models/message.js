const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const User = require('./user');
const Event = require('./event');

const message = new Schema({
    eventId: { type: Schema.Types.ObjectId, ref: 'Event' },
    userId: { type: Schema.Types.ObjectId, ref: 'User' },
    content: { type: String, required: true},
    sentAt: { type: Date, required: true }
});

module.exports = mongoose.model('message', message);
