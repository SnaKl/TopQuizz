import { body, param } from 'express-validator';

export const QuestionValidator = {
	// username must be an email
	bodyDescriptionValidator: body('description')
	.withMessage('Question description')
	.notEmpty(),	

	bodyAnswerListValidator: body('anwserList')
		.isArray({ min: 4, max: 4 })
		.withMessage('4 possible anwsers are required')
		.notEmpty(),
	
	bodyThemeValidator: body('_themeID')
	.notEmpty()
	.withMessage('Theme needs to be supplied to record a question'),

	paramTheme : param('_themeID')
	.notEmpty(),

	optionalBodyDescription : body('description')
	.notEmpty()
	.optional(),

	optionalBodyAnswerList : body('_themeID')
	.notEmpty()
	.optional(),
};

export function createOrUpdateQuestion() {
	return [
		QuestionValidator.bodyDescriptionValidator,
		QuestionValidator.bodyAnswerListValidator,
		QuestionValidator.bodyThemeValidator
	];
}

export function getQuestionByTheme(){
	return [QuestionValidator.paramTheme];
}

export function deleteQuestionByTheme(){
	return[
		QuestionValidator.paramTheme,
		QuestionValidator.optionalBodyDescription,
		QuestionValidator.optionalBodyAnswerList
	]
}
