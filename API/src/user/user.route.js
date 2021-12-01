import { Router } from 'express';
var router = Router();
import * as UserController from './user.controller';
import Validator from '../middleware/validator';
import * as UserValidator from './user.validator';
import Auth from '../middleware/auth';

/**
 * @route GET api/users
 * @desc    users route
 * @access  Private
 */
router.get('/', Auth, UserController.getUser);

router.put(
	'/',
	UserValidator.updateUser(),
	Validator,
	Auth,
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
	UserValidator.getUserByNickname(),
	Validator,
	Auth,
	UserController.getUserByNickname,
);

router.put(
	'/:nickname',
	UserValidator.updateUserByNickname(),
	Validator,
	Auth,
	UserController.updateUserByNickname,
);

router.delete(
	'/:nickname',
	UserValidator.deleteUserByNickname(),
	Validator,
	Auth,
	UserController.deleteUserByNickname,
);

export default router;
