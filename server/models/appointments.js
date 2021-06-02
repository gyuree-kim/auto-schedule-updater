const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const ChatRooms = require('./chatRooms');
const User = require('./user');

const appointmentSchema = new Schema({
    userId: { type: String, ref: 'User' },
    chatRoomId: { type: Schema.Types.ObjectId, ref: 'ChatRooms' },
    date: String,
    time: String,
    location: String,
    createdAt: Date,
    updatedAt: Date
});

module.exports = mongoose.model('appointment', appointmentSchema);
