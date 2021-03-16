var express = require('express');
var app = express();
var port = process.env.PORT || 8080;

class User{
  constructor() {

  }
}

app.get('/', function (req, res) {
  var user = { name:'太郎', age:32, tel:'080-1234-5678' };
  res.json(user);
});

app.listen(port, function(){
  console.log("Listening on " + port);
});

