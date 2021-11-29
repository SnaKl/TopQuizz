import { body, check } from 'express-validator';

export function updateQuestion() {
	return [
		// username must be an email
		body('description').withMessage('Question description'),
		// password must be at least 5 chars long
		body(anwserList)
			.isArray({ min: 4, max: 4 })
			.withMessage('4 possible anwsers are required'),
	];
}
