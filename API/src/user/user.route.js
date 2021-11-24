const express = require('express');
var router = express.Router();
const User = require('./user.model');

/**
 * @route GET api/users
 * @desc    users route
 * @access  Private
 */
router
	.route('/')
	.get((req, res, next) => {
		console.log('get all users');
		next();
	})
	.post((req, res) => {
		console.log('Create user');
	});

router
	.route('/:nickName')
	.get((req, res) => {
		console.log('get user');
	})
	.put((req, res) => {
		console.log('update user');
	})
	.delete((req, res) => {
		console.log('delete user');
	});

module.exports = router;
