let express = require('express');
let app = express();
let port = process.env.PORT || 8080;

app.set("view engine", "ejs");
app.set('views', __dirname + '/public');

app.get('/', function (req, res) {
  res.render("./index.ejs",{message:'mes'})
});

app.get('/user/:name', (req, res) => {
  let queryUser = req.params.name;
  res.status(200).send(queryUser);
});

app.get('/place/:name', (req, res) => {
  let queryUser = req.params.name;
  res.status(200).send(queryUser);
});
app.get('/profile', (req, res) => {
  res.status(200).send("プロフィールが表示されます");
});

app.listen(port, function(){
  console.log("Listening on " + port);
});

