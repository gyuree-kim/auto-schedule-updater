const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const userSchema = new Schema({
    name: String,
    email: {type: String, unique: true},
    password: String,
    // created_at: Strings
});

module.exports = mongoose.model('member', userSchema);