const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const chatRoomSchema = new Schema({
    id: { type: Number, unique: true },
    users: [Number],      //user id list
    messages: [Number], //message id list
    createdAt: Date
},
{
    timestamps: true
});

const chatRooms = new Schema({
    userId: Number, //fk
    chatRooms: [Number], //chatroom id list
    updatedAt: Date
},
{
    timestamps: true
});

module.exports = mongoose.model('chatRooms', chatRooms);
