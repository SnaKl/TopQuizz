import dotenv from 'dotenv';
dotenv.config();

import express from 'express';
import bodyParser from 'body-parser';
const app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use(express.static('./public/uploads'));

import userRoutes from './user/user.route.js';
app.use('/api/user', userRoutes);

import authRoutes from './auth/auth.route.js';
app.use('/api/auth', authRoutes);

app.get('/', (req, res) => {
	res.send('TopQuizz API');
});

export default app;
