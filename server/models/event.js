const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Message = require('./message');
)
const event = new Schema({
    messageId: { type: Schema.Types.ObjectId, ref: 'Message' },
    date: String,
    time: String,
    location: String,
    createdAt: Date,
    updatedAt: Date
});

module.exports = mongoose.model('event', event);
