const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const messageSchema = './message.js'; //??
const user = './user.js'; //??

const chatRoom = new Schema({
    // id: {type: String, unique: true},
    users: [user], //??
    lastUpdated: String,
    messageList: [messageSchema]
    
});

module.exports = mongoose.model('chatRoom', chatRoom);
