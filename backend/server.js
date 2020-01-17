const  createError = require('http-errors');
const  express = require('express');
const  path = require('path');
const  cookieParser = require('cookie-parser');
const  logger = require('morgan');
const  mysql = require('mysql');
const bodyparser = require('body-parser');
var url = require("url");
//Status codes defined in external file
require('./http_status.js');

var app = express();

const  connectionPool = mysql.createPool({
    connectionLimit: 15,
    host:'localhost',
    port:'3305',
    user:'root',
    password:'root',
    database:'testdb',
    insecureAuth : true,
    debug: false
});

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "http://localhost:8080"); // update to match the domain you will make the request from
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});


// app.use(function(req, res, next) {
//     res.header("Access-Control-Allow-Origin", "http://localhost:8081"); // update to match the domain you will make the request from
//     res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//     next();
// });


var sql = "SELECT * FROM cars";
app.get('/cars', (req, res)=>{
    connectionPool.query(sql, (err, rows, fields) => {
        if (!err)
            res.send(rows);
        else
            console.log(err);
    });
});

app.get('/cars/:id', (req, res)=>{
    connectionPool.query('SELECT * FROM cars WHERE car_id = ?',[req.params.id] , (err, rows, fields)=> {
        if (!err)
            res.send(rows);
        else
            console.log(err);
    });
});

app.get('/dealers', (req, res)=>{
    connectionPool.query('SELECT * FROM dealers', (err, rows, fields)=> {
        if (!err)
            res.send(rows);
        else
            console.log(err);
    });
});

app.get('/dealers/:id', (req, res)=>{
    connectionPool.query('SELECT * FROM dealers WHERE dealer_id = ?',[req.params.id] , (err, rows, fields)=> {
        if (!err)
            res.send(rows);
        else
            console.log(err);
    });
});

app.listen(3000,()=>console.log('Express server is running at 3000'));