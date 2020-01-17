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

const  connectionPool = mysql.createPool({
    connectionLimit: 15,
    host:'localhost',
    port:'3305',
    user:'root',
    password:'root',
    database:'testdb',
    insecureAuth : true,
    debug: true
});

// connectionPool.connect((err)=>{
//     if(!err)
//         console.log('DB connection succeded');
//     else
//         console.log('DB connection failed \n Error : ' + JSON.stringify(err,undefined,2));
// });



var app = express();

// // view engine setup
// app.set('views', path.join(__dirname, 'views'));
// app.set('view engine', 'pug');
//
// app.use(logger('dev'));
// app.use(express.json());
// app.use(express.urlencoded({ extended: false }));
// app.use(cookieParser());
// app.use(express.static(path.join(__dirname, 'public')));
// // var indexRouter = require('./routes/index');
// // var usersRouter = require('./routes/users');
// app.use('/', indexRouter);
// app.use('/users', usersRouter);
//
// // catch 404 and forward to error handler
// app.use(function(req, res, next) {
//     next(createError(404));
// });

// error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
});

app.listen(3000,()=>console.log('Express server is running at 3000'));

//Set up the application to handle GET requests sent to the user path
app.get('/cars/*', handleGetRequest);//Subfolders
app.get('/cars', handleGetRequest);


/* Handles GET requests sent to web service.
   Processes path and query string and calls appropriate functions to
   return the data. */
function handleGetRequest(request, response){
    //Parse the URL
    var urlObj = url.parse(request.url, true);

    //Extract object containing queries from URL object.
    var queries = urlObj.query;

    //Get the pagination properties if they have been set. Will be  undefined if not set.
    var numItems = queries['num_items'];
    var offset = queries['offset'];

    //Split the path of the request into its components
    var pathArray = urlObj.pathname.split("/");

    //Get the last part of the path
    var pathEnd = pathArray[pathArray.length - 1];

    //If path ends with 'cars' we return all cereals
    if(pathEnd === 'cars'){
        getTotalCarsCount(response, numItems, offset);//This function calls the getAllCereals function in its callback
        return;
    }

    //If path ends with cereals/, we return all cereals
    if (pathEnd === '' && pathArray[pathArray.length - 2] === 'cars'){
        getTotalCarsCount(response, numItems, offset);//This function calls the getAllCereals function in its callback
        return;
    }

    //If the last part of the path is a valid user id, return data about that user
    var regEx = new RegExp('^[0-9]+$');//RegEx returns true if string is all digits.
    if(regEx.test(pathEnd)){
        getCereal(response, pathEnd);
        return;
    }

    //The path is not recognized. Return an error message
    response.status(HTTP_STATUS.NOT_FOUND);
    response.send("{error: 'Path not recognized', url: " + request.url + "}");
}


/** When retrieving all cereals we start by retrieving the total number of cereals
 The database callback function will then call the function to get the cereal data
 with pagination */
function getTotalCarsCount(response, numItems, offset){
    var sql = "SELECT COUNT(*) FROM cars_test";

    //Execute the query and call the anonymous callback function.
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Get the total number of items from the result
        var totNumItems = result[0]['COUNT(*)'];

        //Call the function that retrieves all cereals
        getAllCars(response, totNumItems, numItems, offset);
    });
}


/** Returns all of the cereals, possibly with a limit on the total number of items returned and the offset (to
 *  enable pagination). This function should be called in the callback of getTotalCerealsCount  */
function getAllCars(response, totNumItems, numItems, offset){
    //Select the cereals data using JOIN to convert foreign keys into useful data.
    var sql = "SELECT cars.car_id, cars.car_title, cars.car_price " +
        "FROM cars_test " +
        "ORDER BY cars.car_id ";

    //Limit the number of results returned, if this has been specified in the query string
    if(numItems !== undefined && offset !== undefined ){
        sql += "LIMIT " + numItems + " OFFSET " + offset;
    }

    //Execute the query
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Create JavaScript object that combines total number of items with data
        var returnObj = {totNumItems: totNumItems};
        returnObj.cereals = result; //Array of data from database

        //Return results in JSON format
        response.json(returnObj);
    });
}


/** Returns the cereal with the specified ID */
function getCar(response, id){
    //Build SQL query to select cereal with specified id.
    var sql = "SELECT cars.car_id, cars.car_title, cars.car_price" +
        "FROM  cars_test" +
        "WHERE cars.car_id=" + id;

    //Execute the query
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Output results in JSON format
        response.json(result);
    });
}

/* Handles GET requests sent to web service.
   Processes path and query string and calls appropriate functions to
   return the data. */
function handleGetRequest(request, response){
    //Parse the URL
    var urlObj = url.parse(request.url, true);

    //Extract object containing queries from URL object.
    var queries = urlObj.query;

    //Get the pagination properties if they have been set. Will be  undefined if not set.
    var numItems = queries['num_items'];
    var offset = queries['offset'];

    //Split the path of the request into its components
    var pathArray = urlObj.pathname.split("/");

    //Get the last part of the path
    var pathEnd = pathArray[pathArray.length - 1];

    //If path ends with 'cereals' we return all cereals
    if(pathEnd === 'cars'){
        getTotalCarsCount(response, numItems, offset);//This function calls the getAllCereals function in its callback
        return;
    }

    // //If path ends with cereals/, we return all cereals
    if (pathEnd === '' && pathArray[pathArray.length - 2] === 'cars'){
        getTotalCarsCount(response, numItems, offset);//This function calls the getAllCereals function in its callback
        return;
    }

    //If the last part of the path is a valid user id, return data about that user
    var regEx = new RegExp('^[0-9]+$');//RegEx returns true if string is all digits.
    if(regEx.test(pathEnd)){
        getCar(response, pathEnd);
        return;
    }

    //The path is not recognized. Return an error message
    response.status(HTTP_STATUS.NOT_FOUND);
    response.send("{error: 'Path not recognized', url: " + request.url + "}");
}


module.exports = app;
