import db from './config/db';
import app from './app';

db.connect();
db.mongoose.connection.once('open', () => {
	console.log('Database connected');
	app.listen(process.env.SERVER_PORT, () => {
		console.log(
			`API listening on ${process.env.SERVER_ADDRESS}:${process.env.SERVER_PORT}`,
		);
	});
});
