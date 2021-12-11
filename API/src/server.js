import db from './config/db';
import app from './app';
var http = require('http');

const serverUrl = process.env.SERVER_ADDRESS + ':' + process.env.SERVER_PORT;

db.connect();
db.mongoose.connection.once('open', () => {
	console.log('Database connected');
	app.listen(3000, '0.0.0.0', () => {
		console.log(`API listening on ${serverUrl}`);
	});
});

export { serverUrl };
