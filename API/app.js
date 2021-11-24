// const express = require('express');
const express = require('express');
const app = express();
const mongoose = require('mongoose');
const dotenv = require('dotenv');
dotenv.config();

mongoose.connect(process.env.DATABASE_URL, {
	useNewUrlParser: true,
	useUnifiedTopology: true,
});

const db = mongoose.connection;
db.on('error', (error) => console.log(error));
db.once('open', () => console.log('Connected to database'));

const userRoutes = require('./src/user/user.route');
app.use('/api/user', userRoutes);

app.listen(process.env.SERVER_PORT, () => {
	console.log(
		`API listening on ${process.env.SERVER_ADDRESS}:${process.env.SERVER_PORT}`,
	);
});
