import { body, param, query } from 'express-validator';

export const UserValidator = {
	paramNickname: param('nickname')
		.trim()
		.escape()
		.isAlphanumeric()
		.isLength({ min: 3, max: 20 }),

	bodyNickname: body('nickname')
		.trim()
		.escape()
		.notEmpty()
		.withMessage('Nickname required')
		.isAlphanumeric()
		.withMessage('Nickname need to bo alphanumeric')
		.isLength({ min: 3, max: 20 })
		.withMessage('Nickname must be between 3 and 20 characters '),

	queryNickname: query('nickname')
		.trim()
		.escape()
		.notEmpty()
		.withMessage('Nickname required')
		.isAlphanumeric()
		.withMessage('Nickname need to bo alphanumeric')
		.isLength({ min: 3, max: 20 })
		.withMessage('Nickname must be between 3 and 20 characters '),

	bodyPassword: body('password')
		.trim()
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
		),

	queryPassword: query('password')
		.trim()
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
		),

	optionalBodyPassword: body('password')
		.trim()
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
		)
		.optional(),

	bodyEmail: body('email').isEmail().normalizeEmail(),
	optionalBodyEmail: body('email').isEmail().normalizeEmail().optional(),
};

// Minimum eight and maximum 20 characters, at least
// one uppercase letter, one lowercase letter, one number and one special character:

export function updateUser() {
	return [
		UserValidator.optionalBodyEmail,
		// UserValidator.optionalBodyPassword,
	];
}
export function createUser() {
	return [
		UserValidator.bodyNickname,
		UserValidator.bodyEmail,
		UserValidator.bodyPassword,
	];
}
export function getUserByNickname() {
	return [UserValidator.paramNickname];
}
export function deleteUserByNickname() {
	return [UserValidator.paramNickname];
}
export function updateUserByNickname() {
	return [
		UserValidator.paramNickname,
		UserValidator.optionalBodyEmail,
		UserValidator.optionalBodyPassword,
	];
}
