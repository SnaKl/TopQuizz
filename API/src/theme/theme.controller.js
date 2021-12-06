import * as ThemeService from './theme.service';

export async function getAllTheme(req, res) {
	const themes = await ThemeService.findTheme();
	res.send(themes);
}
export async function createTheme(req, res) {
	try {
		const { title, description } = req.body;

		if (await ThemeService.findTheme({ title }, 'id', 1))
			return res.status(400).json({ msg: 'Title name is already used' });

		if (!ThemeService.createTheme(title, description))
			return res.status(500).send('Server error');

		res.status(201).end();
	} catch (e) {
		console.error(e);
		res.status(500).send('Server error');
	}
}

export async function getThemeByTitle(req, res) {
	const title = req.params.title;
	const theme = await ThemeService.findTheme({ title }, '', 1);
	res.send(theme);
}

export function updateThemeByTitle(req, res) {
	throw new Error('Function not implemented.');
}

export function deleteThemeByTitle(req, res) {
	// only admin can do it
	throw new Error('Function not implemented.');
}
