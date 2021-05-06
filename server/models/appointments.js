const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const ChatRooms = require('./chatRooms');
const User = require('./user');

const appointmentSchema = new Schema({
    chatRoomId: { type: Schema.Types.ObjectId, ref: 'ChatRooms' },
    date: String,
    time: String,
    location: String,
    createdAt: Date,
    updatedAt: Date
});

const appointments = new Schema({
    userId: { type: Schema.Types.ObjectId, ref: 'User' },
    appointments: [{ type: Schema.Types.ObjectId, ref: 'Appointment' }],
    updatedAt: Date
});

var Appointment = mongoose.model('Appointment', appointmentSchema);
module.exports = mongoose.model('appointments', appointments);
