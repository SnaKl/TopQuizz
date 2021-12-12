import fs from 'fs';
import * as ThemeService from './theme.service';

import { serverUrl } from '../server';

export async function getAllTheme(req, res) {
	const themes = await ThemeService.findTheme();
	res.send({ themes });
}
export async function createTheme(req, res) {
	const file = req.file;
	if (!file)
		return res.status(400).send('You need to provide an image theme');

	var { title, description } = req.body;

	if (!title) {
		fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a title theme');
	}

	const theme = await ThemeService.findTheme({ title }, '_id', 1);
	if (theme) {
		fs.unlinkSync(file.path);
		return res.status(400).send('Theme title already taken');
	}

	if (!description) {
		fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a description');
	}

	if (
		!ThemeService.createTheme(
			title,
			description,
			serverUrl + file.path.slice(14, file.path.length),
		)
	)
		return res.status(500).send('Server error');

	res.status(201).end();
}

export async function getThemeByTitle(req, res) {
	const title = req.params.title;
	const theme = await ThemeService.findTheme({ title }, '', 1);
	res.send({ theme });
}

//TODO updateThemeByTitle
export function updateThemeByTitle(req, res) {
	throw new Error('Function not implemented.');
}

//TODO deleteThemeByTitle
export function deleteThemeByTitle(req, res) {
	// only admin can do it
	throw new Error('Function not implemented.');
}
