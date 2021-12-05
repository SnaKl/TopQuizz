import { Router } from 'express';
var router = Router();
import Theme from './theme.model';

/**
 * @route GET api/themes
 * @desc    themes route
 * @access  Private
 */
router
	.route('/')
	.get((req, res) => {
		res.send('get all themes');
	})
	.post((req, res) => {
		res.send('Create theme');
	});

router
	.route('/:title')
	.get((req, res) => {
		res.send('get theme by title');
	})
	.put((req, res) => {
		res.send('update theme');
	})
	.delete((req, res) => {
		res.send('delete theme');
	});

export default router;
