const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const User = require('./user');
const Message = require('./message');

const chatRoomSchema = new Schema({
    id: { type: Number, unique: true },
    users: [{ type: Schema.Types.ObjectId, ref: 'User' }],
    messages: [{ type: Schema.Types.ObjectId, ref: 'Message' }],
    createdAt: Date
},
{
    timestamps: true
});

const chatRooms = new Schema({
    userId: { type: Schema.Types.ObjectId, ref: 'User' },
    chatRoomId: [{ type: Schema.Types.ObjectId, ref: 'ChatRooms' }],
    updatedAt: Date
},
{
    timestamps: true
});

module.exports = mongoose.model('chatRooms', chatRooms);
