const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const user = new Schema({
    id: { type: Number, unique: true },
    name: String,
    password: String,
    createdAt: Date
},
{
    timestamps: true
});

module.exports = mongoose.model('user', user);