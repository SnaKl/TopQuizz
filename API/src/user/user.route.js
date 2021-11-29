import { Router } from 'express';
var router = Router();
import * as UserController from './user.controller';
import Validator from '../middleware/validator';
import * as UserValidator from './user.validator';

/**
 * @route GET api/users
 * @desc    users route
 * @access  Private
 */
router.get('/', UserController.getUser);

router.put(
	'/',
	UserValidator.updateUser(),
	Validator,
	UserController.updateUser,
);

router.post(
	'/',
	UserValidator.createUser(),
	Validator,
	UserController.createUser,
);

router.delete('/', UserController.deleteUser);

router.get('/all', UserController.getUsers);

router.get(
	'/:nickname',
	UserValidator.getUserByNickname(),
	Validator,
	UserController.getUserByNickname,
);

router.put(
	'/:nickname',
	UserValidator.updateUserByNickname(),
	Validator,
	UserController.updateUserByNickname,
);

router.delete(
	'/:nickname',
	UserValidator.deleteUserByNickname(),
	Validator,
	UserController.deleteUserByNickname,
);

export default router;
