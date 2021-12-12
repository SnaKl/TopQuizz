import { body, param } from 'express-validator';
export const QuestionValidator = {
	paramTheme: param('theme').notEmpty(),

	bodyListAnsweredQuestion: body('listAnsweredQuestion')
		.isArray()
		.withMessage('need to be an array'),

	paramNbQuestion: param('nbQuestion')
		.isInt({ min: 1 })
		.withMessage('need to be an numeric with min 1'),

	bodyVoteQuestion: body('vote')
		.exists()
		.withMessage('vote is Requiered')
		.isString()
		.withMessage('vote must be a String')
		.isIn(['upVote', 'downVote'])
		.withMessage('vote does contain invalid value'),

	paramQuestionId: param('questionId')
		.exists()
		.withMessage('questionId is Requiered')
		.isMongoId()
		.withMessage('questionId must be a valid ID'),
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

export function putVoteQuestionById() {
	return [
		QuestionValidator.bodyVoteQuestion,
		QuestionValidator.paramQuestionId,
	];
}

export function getQuestionToValidate() {
	return [QuestionValidator.paramTheme];
}

export function getQuestionByTheme() {
	return [QuestionValidator.paramTheme];
}

export function deleteQuestionByTheme() {
	return [QuestionValidator.paramTheme];
}
