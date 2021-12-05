import { Router } from 'express';
var router = Router();
import * as AuthController from './auth.controller';
import Validator from '../middleware/validator';
import * as AuthValidator from './auth.validator';

router.get(
	'/login',
	AuthValidator.loginUser(),
	Validator,
	AuthController.login,
);

export default router;
