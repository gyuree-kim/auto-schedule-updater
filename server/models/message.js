const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const message = new Schema({
    sender: String,
    content: String,
    sentAt: String
});

module.exports = mongoose.model('message', message);
