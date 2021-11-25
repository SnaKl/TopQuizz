import { Router } from 'express';
var router = Router();
import User from './user.model.js';

/**
 * @route GET api/users
 * @desc    users route
 * @access  Private
 */
router
	.route('/')
	.get((req, res) => {
		res.send('get all users');
	})
	.post((req, res) => {
		res.send('Create user');
	});

router
	.route('/:nickName')
	.get((req, res) => {
		res.send('get user');
	})
	.put((req, res) => {
		res.send('update user');
	})
	.delete((req, res) => {
		res.send('delete user');
	});

export default router;
