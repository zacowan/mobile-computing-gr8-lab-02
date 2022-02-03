// Simple server to accept certain requests and return JSON objects
var expr   = require('express');
var fs     = require('fs');
var _      = require('lodash');
var app    = expr();

// Configure the app
const FILE = "temp.json";
const PORT = 8080;
app.use(expr.static(__dirname + '/public'));

// Open the posts
var content = JSON.parse(fs.readFileSync(FILE));

// Define routes
app.get("/", function (req, res) {  // Home page
	res.send("Home Page");
});

app.get("/api", function (req, res) {  // API calls
	res.send(content);
});

app.get("/api/s", function (req, res) {
	res.send({"results": content.books});
});

app.get("/api/s/:query", function (req, res) {
	var query = req.params.query;

	console.log("Searching for " + query);

	res.send({"results": _.filter(content.books, function (q) {
		var match = false;

		for (var key in q) {
			if (q.hasOwnProperty(key) && key !== "id" && key !== "image") {
				if (q[key].indexOf(query) !== -1) {
					match = true;
					break;
				}
			}
		}

		return match;
	})});
});

app.get("/api/r/books/:id", function (req, res) {
	var id = parseInt(req.params.id, 10);

	// Find the corresponding result
	res.send(_.find(content.books, function (q) {
		return (q.id === id);
	}));
});


// Define the App
app.listen(PORT, function () {
	console.log("Running on port " + PORT);
});
