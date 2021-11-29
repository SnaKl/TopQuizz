import { body, param } from 'express-validator';

const paramUserNickname = param('nickname')
	.escape()
	.isAlphanumeric()
	.isLength({ min: 3, max: 20 });

const bodyUserPassword = body('password')
	.escape()
	.notEmpty()
	.withMessage('Password required')
	.isLength({ min: 3, max: 30 })
	.withMessage('Password must be between 3 and 30 characters')
	.matches(/^[A-Za-z0-9 .,'!&]+$/);

const bodyUserEmail = body('email').isEmail();

export function updateUser() {
	return [bodyUserEmail.optional(), bodyUserPassword.optional()];
}
export function createUser() {
	return [
		// username must be an emai
		body('nickname')
			.escape()
			.isAlphanumeric()
			.isLength({ min: 3, max: 20 }),
		bodyUserEmail,
		bodyUserPassword,
	];
}

export function getUserByNickname() {
	return [paramUserNickname];
}
export function deleteUserByNickname() {
	return [paramUserNickname];
}
export function updateUserByNickname() {
	return [
		paramUserNickname,
		bodyUserEmail.optional(),
		bodyUserPassword.optional(),
	];
}
