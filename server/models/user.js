const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const user = new Schema({
    id: { type: String, unique: true }, // nickname
    name: { type: String, required: true }, //real name
    password: { type: String, required: true },
    createdAt: { type: Date, required: true }
},
{
    timestamps: true
});

module.exports = mongoose.model('user', user);