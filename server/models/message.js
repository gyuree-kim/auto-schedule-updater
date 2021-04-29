const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const chatRoom = require('./chatRoom');

const message = new Schema({
    id: { type: Number, required: true, unique: true },
    chatRoomId: chatRoom.id, //pk
    sender: String,
    content: String,
    sentAt: String
});

module.exports = mongoose.model('message', message);
