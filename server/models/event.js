const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Message = require('./message');

const EventType = {
    infectedEvent: "infectedEvent",
    infectedCount: "infectedCount"
}

const event = new Schema({    
    messageId: { type: Schema.Types.ObjectId, ref: 'Message' },
    type: String,
    count: Number,
    date: String,
    time: String,
    location: String,
    createdAt: Date,
    updatedAt: Date
});

module.exports = mongoose.model('event', event);