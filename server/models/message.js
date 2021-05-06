const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const ChatRooms = require('./chatRooms');
const User = require('./user');

const message = new Schema({
    chatRoomId: { type: Schema.Types.ObjectId, ref: 'ChatRooms' },
    sender: { type: Schema.Types.ObjectId, ref: 'User' },
    content: { type: String, required: true},
    isRead: Boolean,
    createdAt: Date
},
{ 
    timestamps: true 
});

module.exports = mongoose.model('message', message);
