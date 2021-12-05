import db from './config/db';
import app from './app';

const serverUrl = process.env.SERVER_ADDRESS + ':' + process.env.SERVER_PORT;

db.connect();
db.mongoose.connection.once('open', () => {
	console.log('Database connected');
	app.listen(process.env.SERVER_PORT, () => {
		console.log(`API listening on ${serverUrl}`);
	});
});

export { serverUrl };
