const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const messageSchema = new Schema({
    sender: String,
    content: String,
    sentAt: String
});

module.exports = mongoose.model('messageList', messageList);
