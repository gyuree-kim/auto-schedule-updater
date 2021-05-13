const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const ChatRooms = require('./chatRooms');
const User = require('./user');

const message = new Schema({
    chatRoomId: { type: Schema.Types.ObjectId, ref: 'ChatRooms', required: true },
    sender: { type: String, ref: 'User', required: true },
    content: { type: String, required: true},
    isRead: Boolean,
    createdAt: { type: Date, required: true }
},
{ 
    timestamps: true 
});

module.exports = mongoose.model('message', message);
