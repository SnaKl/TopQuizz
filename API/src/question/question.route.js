import { Router } from 'express';
var router = Router();
import * as QuestionController from './question.controller';

/**
 * @route GET api/question
 * @desc question route
 * @access Public
 */
router.get('/', QuestionController.getTheme);

/**
 * @route PUT api/question
 * @desc update question route
 * @access Private
 */
router.put('/', QuestionController.updateTheme);

/**
 * @route POST api/question
 * @desc create question route
 * @access Public
 */
router.post('/', QuestionController.createTheme);

/**
 * @route GET api/question/:id
 * @desc get question by id route
 * @access Public
 */
router.get('/:id', QuestionController.getThemeByTitle);

/**
 * @route PUT api/question/:id
 * @desc update question by id route
 * @access Public
 */
router.put('/:id', QuestionController.updateThemeByTitle);

/**
 * @route DELETE api/question/:id
 * @desc question by id route
 * @access Public
 */
router.delete('/:id', QuestionController.deleteQuestionById);

/**
 * @route GET api/question/:themeID
 * @desc get question by theme id route
 * @access Public
 */
router.get('/:themeID', QuestionController.getQuestionByTheme);

/**
 * @route DELETE api/question/:themeID
 * @desc delete question by theme id route
 * @access Private
 */
router.delete('/themeID', QuestionController.deleteQuestionByTheme);

export default router;
