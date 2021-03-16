var express = require('express');
let ejs = require('ejs');
var app = express();
var port = process.env.PORT || 8080;

app.set("view engine", "ejs");
app.set('views', __dirname + '/public');

class User{
  constructor() {

  }
}

app.get('/', function (req, res) {
  var user = { name:'太郎', age:32, tel:'080-1234-5678' };
  res.render("./index.ejs")
});

app.listen(port, function(){
  console.log("Listening on " + port);
});

