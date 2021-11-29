import { body, param } from 'express-validator';

const paramUserNickname = param('nickname')
	.escape()
	.isAlphanumeric()
	.isLength({ min: 3, max: 20 });

const bodyUserPassword = body('password')
	.escape()
	.notEmpty()
	.withMessage('Password required')
	.isLength({ min: 4, max: 30 })
	.withMessage('Password must be between 4 and 30 characters')
	.matches(
		/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{4,20}$/,
	)
	.withMessage(
		'Password have at least one uppercase letter, one lowercase letter, one number and one special character',
	);
// Minimum eight and maximum 20 characters, at least
// one uppercase letter, one lowercase letter, one number and one special character:
const bodyUserEmail = body('email').isEmail();

export function updateUser() {
	return [bodyUserEmail.optional(), bodyUserPassword.optional()];
}
export function createUser() {
	return [
		// username must be an emai
		body('nickname')
			.escape()
			.notEmpty()
			.withMessage('Nickname required')
			.isAlphanumeric()
			.withMessage('Nickname need to bo alphanumeric')
			.isLength({ min: 3, max: 20 })
			.withMessage('Nickname must be between 3 and 20 characters '),
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
