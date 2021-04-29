const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const appointmentSchema = new Schema({
    date: String,
    time: String,
    location: String
});

const appointmentList = new Schema({
    // chatId: {type: String, unique: true},
    appointmentList: [appointmentSchema]
    
});

module.exports = mongoose.model('appointmentList', appointmentList);
