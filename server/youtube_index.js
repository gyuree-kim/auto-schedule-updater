var mongodb = require('mongodb');
var ObjectID = mongodb.ObjectID; 
var crypto = require('crypto');
var express = require('express');
var bodyParser = require('body-parser');

// Password ultils
// create fuction to random salt
var genRandomString = function(length) {
    return crypto.randomBytes(Math.ceil(length/2))
        .toStrint('hex') //convert to hexa format
        .slice(0, length);
};

var sha512 = function(password, salt){
    var hash = crypto.createHmax('sha512', salt);
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt: salt,
        passwordHash: value
    }
};

function saltHashPassword(userPassword){
    var salt = genRandomString(16); //create 16 random char
    var passwordData = sha512(userPassword, salt);
    return passwordData
}

function checkHashPassword(userPassword, salt){
    var passwordData = sha512(userPassword, salt);
    return passwordData;
}

// Create Express Service
var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

// Create Mongodb client
var MongoClient = mongodb.MongoClient;

// Connection URL
var url = 'mongodb://localhost:27017';

MongoClient.connect(url, {useNewUrlParser: true}, function(err, client){
    if(err)
    {
        console.log('Unable to connect to the mongoDB server.error', err);
    }
    else 
    {
        // Register
        app.post('/register', (req, res, next) => {
            var post_data = req.body;

            var plaint_password = post_data.password;
            var hash_data = saltHashPassword(plaint_password);

            var password = hash_data.passwordHash; //save password hash
            var salt = hash_data.salt;

            var name = post_data.name;
            var email = post_data.email;

            var insertJson = {
                'email': email,
                'password': password,
                'salt': salt,
                'name': name
            };
            var db = client.db('auto-schedule-updater');

            // check exists email
            db.collection('user')
                .find({'email':email}).count(function(err, number){
                    if(number != 0)
                    {
                        res.json('Email already exists');
                        console.log('Email already exists');
                    }
                    else 
                    {
                        //insert data
                        db.collection('user')
                            .insertOne(insertJson, function(err, res){
                                res.json('Registration Succeess');
                                console.log('Registration Succeess');
                            })
                    }
                })
        });

        // login
        app.post('/login', (req, res, next) => {
            var post_data = req.body;

            var email = post_data.email;
            var userPassword = post_data.password;

            var db = client.db('auto-schedule-updater');

            // check exists email
            db.collection('user')
                .find({'email':email}).count(function(err, number){
                    if(number == 0)
                    {
                        res.json('Email not exists');
                        console.log('Email not exists');
                    }
                    else 
                    {
                        //insert data
                        db.collection('user')
                            .findOne({'email':email}, function(err, user){
                                var salt = user.salt;
                                var hashed_password = checkHashPassword(userPassword, salt).passwordHash;
                                var encrypted_password = user.password;
                                if(hashed_password == encrypted_password)
                                {
                                    res.json('Login success');
                                    console.log('Login success');
                                }
                            })
                    }
                })
        });

        // Start Web Server
        app.listen(3000, () => {
            console.log('Connected to MongoDB Server, WebService running on port 3000')
        })
    }
});
