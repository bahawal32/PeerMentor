var mongodb = require('mongodb');
var ObjectID = mongodb.ObjectID;
var crypto = require('crypto');
var express = require('express');
var bodyParser = require('body-parser');


var genRandomString = function(lenght){
    return crypto.randomBytes(Math.ceil(lenght/2))
    .toString('hex')
    .slice(0,lenght);
};

var sha512 = function(password,salt){
    var hash = crypto.createHmac('sha512',salt);
    hash.update(password);
    var value =hash.digest('hex');
    return {
        salt:salt,
        passwordHash:value
    };
};

function saltHashPassword(userPassword){
    var salt = genRandomString(16);
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}

function checkHashPassword(userPassword,salt){
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

var MongoClient = mongodb.MongoClient;

var url = 'mongodb://localhost:27017';
MongoClient.connect(url,{useNewUrlParser: true},function(err , client){
    if(err){
        console.log('unable to connect to mongodb server error ',err);
    }else{
        //Register
        app.post('/register',(request,response,next)=>{
            var post_data = request.body;

            var plain_password =  post_data.password;
            var hash_data = saltHashPassword(plain_password);

            var password = hash_data.passwordHash;
            var salt = hash_data.salt;

            var name = post_data.name;
            var username = post_data.username;

            var insertJson = {
                'username' : username,
                'password' : password,
                'salt' : salt,
                'name' : name
            };
            var db = client.db('login');

            //check email exist
            db.collection('user')
                .find({'username': username}).count(function(err,number){
                    if(number != 0)
                    {
                        response.json('Email already exists');
                        console.log('Email already exists');
                    }
                    else
                    {
                        db.collection('user')
                            .insertOne(insertJson,function(error,res){
                                response.json('Registration success');
                                console.log('Registration success');
                            });
                    }

                });


        });
        app.get('/',(request,response,next)=>{
            //response.json("connected");

        });

        app.post('/login',(request,response,next)=>{
            var post_data = request.body;

            var username = post_data.username;
            var userPassword = post_data.password;

           
            var db = client.db('login');

            //check email exist
            db.collection('user')
                .find({'username': username}).count(function(err,number){
                    if(number == 0)
                    {
                        response.json('username not exists');
                        console.log('username not exists');
                    }
                    else
                    {
                        db.collection('user')
                            .findOne({'username': username} , function(err,user){
                                var salt = user.salt;
                                var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                var encrypted_password = user.password;
                                if(hashed_password == encrypted_password)
                                {
                                    response.json('Login success');
                                    console.log('Login success');
                                }
                                else
                                {
                                    response.json('Wrong password');
                                    console.log('Wrong password');
                                }
                            });
                    }

                });


        });


//var express = require('express');
//var app = express();
var server = require('http').createServer(app);
var io = require('socket.io').listen(server);

users = [];
connections = [];

server.listen(process.env.PORT || 3000);
console.log('server running...');

// app.get('/',function(req, res){
//     res.sendFile(__dirname + '/index.html');
// });

io.sockets.on('connection', function(socket){
    connections.push(socket);
    console.log('Connected: %s sockets connected' , connections.length);

    //Disconnect
    socket.on('disconnect', function(data){
        // if(!socket.username) return;
        users.splice(users.indexOf(socket.username),1);
        updateUsernames();
        connections.splice(connections.indexOf(socket),1);
        console.log('Disconnected: %s sockets connected', connections.length);
    });
    
    // send message
    socket.on('send message' , function(data){
            console.log(data)
            io.emit('new message' , {msg: data});
    });



    //broadcast
    socket.on('broadcast' , function(data){
            io.emit('new broadcast' , {username: data});
            console.log(data)
    });

    //new user
    socket.on('new user',function(data, callback){
        callback(true);
        socket.username = data;
        users.push(socket.username);
        updateUsernames();
    });

    function updateUsernames(){
        io.sockets.emit('get users',users);
    }

})


        // app.listen(3000, ()=> {
        //     console.log('Connected');
        // });
    }


});
