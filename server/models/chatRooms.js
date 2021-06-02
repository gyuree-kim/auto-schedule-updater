const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const User = require('./user');
const Message = require('./message');

const chatRoomSchema = new Schema({
    users: [{ type: Schema.Types.String, ref: 'User', required: true }],
    messages: [{ type: Schema.Types.ObjectId, ref: 'Message' }],
    createdAt: Date,
    recentMsg: { type: Schema.Types.ObjectId, ref: 'Message' }
},
{
    timestamps: true
});

module.exports = mongoose.model('chatRoom', chatRoomSchema);
