import { Router } from 'express';
var router = Router();
import * as ThemeController from './theme.controller';

import Auth from '../middleware/auth';
import Validator from '../middleware/validator';
import * as ThemeValidator from './theme.validator';

import upload from '../middleware/upload';

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
router.post('/', Auth, upload('themeImage'), ThemeController.createTheme);

/**
 * @route GET api/theme/:title
 * @desc get theme by title route
 * @access Public
 */
router.get('/:title', ThemeController.getThemeByTitle);

/**
 * @route PUT api/theme/:title
 * @desc update theme by title route
 * @access Admin
 */
//TODO rajouter vérification du role
router.put('/:title', Auth, ThemeController.updateThemeByTitle);

/**
 * @route DELETE api/theme/:title
 * @desc delete theme by title route
 * @access Admin
 */
//TODO rajouter vérification du role
router.delete('/:title', Auth, ThemeController.deleteThemeByTitle);

export default router;
