const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Message = require('./message');

const event = new Schema({    
    messageId: { type: String, ref: 'Message' },
    type: { type: String },
    count: { type: Number, default: null },
    date: { type: String, default: null},
    time: { type: String, default: null },
    location: { type: String },
    createdAt: { type: Date },
    updatedAt: { type: Date }
});

// 사용안하는 모델
const InfectedCountData = new Schema({
    count: Number
})
const InfectedEventData = new Schema({
    date: String,
    time: String
})

module.exports = mongoose.model('event', event);