var express = require('express');
var app = express.createServer();
var port = process.env.PORT || 3000;

class User{
  constructor(uid) {

  }
}

app.get('/', function (req, res) {

});

app.listen(port, function(){
  console.log("Listening on " + port);
});

