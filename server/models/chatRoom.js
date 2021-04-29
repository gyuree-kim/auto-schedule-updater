const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const user = require('./user');
const message = require('./messageList');

const chatRoom = new Schema({
    id: { type: Number, unique: true },
    users: [user],
    lastUpdated: String,
    messagesList: [message]
},
{
    timestamps: ture
});

module.exports = mongoose.model('chatRoom', chatRoom);
