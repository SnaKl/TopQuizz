import { body, param } from 'express-validator';
import { isValidObjectId } from 'mongoose';
export const QuestionValidator = {
	paramTheme: param('theme').notEmpty(),

	bodyListAnsweredQuestion: body('listAnsweredQuestion')
		.isArray()
		.withMessage('need to be an array'),

	paramNbQuestion: param('nbQuestion')
		.isInt({ min: 1 })
		.withMessage('need to be an numerci with min 1'),
};

export function getRandomQuestionByTheme() {
	return [
		QuestionValidator.paramTheme,
		QuestionValidator.bodyListAnsweredQuestion,
	];
}

export function getRandomQuestionsByTheme() {
	return [QuestionValidator.paramTheme, QuestionValidator.paramNbQuestion];
}
export function getQuestionByTheme() {
	return [QuestionValidator.paramTheme];
}

export function deleteQuestionByTheme() {
	return [QuestionValidator.paramTheme];
}
