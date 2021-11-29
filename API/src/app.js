import express from 'express';
import bodyParser from 'body-parser';
const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

import userRoutes from './user/user.route.js';
app.use('/api/user', userRoutes);

app.get('/', (req, res) => {
	res.send('TopQuizz API');
});

export default app;
