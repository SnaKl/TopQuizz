import { Router } from 'express';
var router = Router();
import * as ThemeController from './theme.controller';

import Validator from '../middleware/validator';
import * as ThemeValidator from './theme.validator';

/**
 * @route GET api/theme
 * @desc return all theme
 * @access Public
 */
router.get('/', ThemeController.getAllTheme);

/**
 * @route POST api/theme
 * @desc create theme route
 * @access Public
 */
router.post(
	'/',
	ThemeValidator.createTheme(),
	Validator,
	ThemeController.createTheme,
);

/**
 * @route GET api/theme/:title
 * @desc get theme by title route
 * @access Public
 */
router.get('/:title', ThemeController.getThemeByTitle);

/**
 * @route PUT api/theme/:title
 * @desc update theme by title route
 * @access Public
 */
router.put('/:title', ThemeController.updateThemeByTitle);

/**
 * @route DELETE api/theme/:title
 * @desc delete theme by title route
 * @access Public
 */
router.delete('/:title', ThemeController.deleteThemeByTitle);

export default router;
