import { body } from 'express-validator';

export const ThemeValidator = {
	bodyTitle: body('title')
		.notEmpty()
		.withMessage('Theme title is required')
		.isAlpha()
		.withMessage('Only letters are accepted'),
	bodyDescription: body('description')
		.notEmpty()
		.withMessage('Description is required')
		.isAlpha()
		.withMessage('Only letters are accepted'),
};

export function createTheme() {
	return [ThemeValidator.bodyTitle, ThemeValidator.bodyDescription];
}
