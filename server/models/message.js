const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const ChatRooms = require('./chatRooms');

const message = new Schema({
    id: { type: Number, required: true, unique: true },
    chatRoomId: { type: Schema.Types.ObjectId, ref: 'ChatRooms' },
    sender: { type: String, required: true},
    content: { type: String, required: true},
    createdAt: Date
},
{ 
    timestamps: true 
});

module.exports = mongoose.model('message', message);
