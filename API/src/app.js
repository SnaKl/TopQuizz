// const express = require('express');
import express from 'express';
const app = express();

import userRoutes from './user/user.route.js';
app.use('/api/user', userRoutes);

app.get('/', (req, res) => {
	res.send('TopQuizz API');
});

export default app;
