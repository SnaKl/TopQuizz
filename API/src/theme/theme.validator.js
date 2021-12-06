import { body } from 'express-validator';

export const ThemeValidator = {
	bodyTitle: body('title')
		.escape()
		.notEmpty()
		.withMessage('Theme title is required'),
	bodyDescription: body('description')
		.escape()
		.notEmpty()
		.withMessage('Description is required'),
};

export function createTheme() {
	return [ThemeValidator.bodyTitle, ThemeValidator.bodyDescription];
}
