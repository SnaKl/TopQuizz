import { Router } from 'express';
var router = Router();
import * as UserController from './user.controller';
import Validator from '../middleware/validator';
import * as UserValidator from './user.validator';
import Auth from '../middleware/auth';

import upload from '../middleware/upload';

/**
 * @route GET api/users
 * @desc    users route
 * @access  Private
 */
router.get('/', Auth, UserController.getUser);

router.put(
	'/',
	Auth,
	UserValidator.updateUser(),
	Validator,
	upload('userAvatar'),
	UserController.updateUser,
);

router.post(
	'/',
	UserValidator.createUser(),
	Validator,
	UserController.createUser,
);

router.delete('/', Auth, UserController.deleteUser);

router.get('/all', UserController.getUsers);

router.get(
	'/:nickname',
	Auth,
	UserValidator.getUserByNickname(),
	Validator,
	UserController.getUserByNickname,
);

router.put(
	'/:nickname',
	Auth,
	UserValidator.updateUserByNickname(),
	Validator,
	upload('userAvatar'),
	UserController.updateUserByNickname,
);

router.delete(
	'/:nickname',
	Auth,
	UserValidator.deleteUserByNickname(),
	Validator,
	UserController.deleteUserByNickname,
);

export default router;
