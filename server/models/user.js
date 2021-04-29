const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const user = new Schema({
    id: { type: Number, unique: true },
    name: String,
    email: {type: String, unique: true},
    password: String,
    created_at: String
},
{
    timestamps: true
});

module.exports = mongoose.model('user', user);