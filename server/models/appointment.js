const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const user = require('./user');
const chatRoom = require('./chatRoom');

const appointmentSchema = new Schema({
    id: { type: Number, unique: true },
    chatRoomId: chatRoom.id, //pk
    date: String,
    time: String,
    location: String
});

const appointmentList = new Schema({
    id: { type: Number, unique: true },
    userId: user.id, //pk
    appointmentList: [appointmentSchema]
    
});

module.exports = mongoose.model('appointmentList', appointmentList);
