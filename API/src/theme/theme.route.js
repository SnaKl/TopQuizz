import { Router } from 'express';
var router = Router();
import * as ThemeController from './theme.controller';

/**
 * @route GET api/theme
 * @desc theme route
 * @access Public
 */
router.get('/', ThemeController.getTheme);

/**
 * @route PUT api/theme
 * @desc update theme route
 * @access Private
 */
router.put('/', ThemeController.updateTheme);

/**
 * @route POST api/theme
 * @desc create theme route
 * @access Public
 */
router.post('/', ThemeController.createTheme);

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
router.delete('/:title', ThemeController.updateThemeByTitle);

export default router;
