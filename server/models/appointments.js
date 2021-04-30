const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const appointmentSchema = new Schema({
    id: { type: Number, unique: true },
    chatRoomId: Number, //fk
    date: String,
    time: String,
    location: String,
    createdAt: Date,
    updatedAt: Date
});

const appointments = new Schema({
    id: { type: Number, unique: true },
    userId: Number, //fk
    appointments: [Number], //appointment id list
    updatedAt: Date
});

module.exports = mongoose.model('appointments', appointments);
