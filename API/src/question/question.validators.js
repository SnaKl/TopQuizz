import { body, check } from 'express-validator';

export const QuestionValidator = {
	// username must be an email
	bodyDescriptionValidator: body('description').withMessage(
		'Question description',
	),
	// password must be at least 5 chars long
	bodyAnswerList: body(anwserList)
		.isArray({ min: 4, max: 4 })
		.withMessage('4 possible anwsers are required'),
};

export function createOrUpdateQuestion() {
	return [
		QuestionValidator.bodyDescriptionValidator,
		QuestionValidator.bodyAnswerList,
	];
}

export function updateQuestion() {
	return;
}
