import { Router } from 'express';
var router = Router();
import * as QuestionController from './question.controller';

import Auth from '../middleware/auth';
import Validator from '../middleware/validator';
import * as QuestionValidator from './question.validator';
import upload from '../middleware/upload';

/**
 * @route POST api/question
 * @desc create question
 * @access Public
 */
router.post(
	'/',
	Auth,
	upload('questionImage'),
	QuestionController.createQuestion,
);

/**
 * @route GET api/question
 * @desc get random quetions in theme
 * @access Public
 */
router.get(
	'/randomQuestion/:theme',
	QuestionValidator.getRandomQuestionByTheme(),
	Validator,
	QuestionController.getRandomQuestionByTheme,
);

// req.params.nbQuestion;
/**
 * @route GET api/question
 * @desc get random quetions in theme
 * @access Public
 */
router.get(
	'/randomQuestion/:theme/:nbQuestion',
	QuestionValidator.getRandomQuestionsByTheme(),
	Validator,
	QuestionController.getRandomQuestionsByTheme,
);

/**
 * @route DELETE api/question/:themeID
 * @desc delete question by theme
 * @access Private
 */
router.delete('/theme/:theme', QuestionController.deleteQuestionsByTheme);

/**
 * @route GET api/question
 * @desc get question by id
 * @access Public
 */
router.get('/id/:questionID', QuestionController.getQuestionById);

/**
 * @route PUT api/question
 * @desc update question by id
 * @access Private
 */
router.put('/id/:questionID', QuestionController.updateQuestionById);

/**
 * @route DELETE api/question/:id
 * @desc delete question by id
 * @access Public
 */
router.delete('/id/:questionsID', QuestionController.deleteQuestionById);

export default router;
