import fs from 'fs';
import { Types } from 'mongoose';

import { serverUrl } from '../server';
import * as QuestionService from './question.service';
import * as ThemeService from '../theme/theme.service';

export async function createQuestion(req, res) {
	const file = req.file;
	if (!file)
		return res.status(400).send('You need to provide an image question');

	var { themeTitle, description, anwserList, correctAnswerIndex } = req.body;

	if (!themeTitle) {
		fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a theme');
	}

	const theme = await ThemeService.findTheme(
		{ title: themeTitle },
		'_id nbQuestion',
		1,
	);
	if (!theme) {
		fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a valide theme');
	}

	if (!description) {
		fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a description');
	}

	if (!anwserList) {
		fs.unlinkSync(file.path);
		return res
			.status(400)
			.send(
				'you need to provide a list of answer of this format : answer1///answer2///answer3///answer4',
			);
	}

	anwserList = anwserList.split('///');
	if (anwserList.length !== 4) {
		fs.unlinkSync(file.path);
		return res
			.status(400)
			.send(
				'you need to provide a list of answer of this format : answer1///answer2///answer3///answer4',
			);
	}
	correctAnswerIndex = parseInt(correctAnswerIndex);
	if (correctAnswerIndex < 0 || correctAnswerIndex > 3) {
		fs.unlinkSync(file.path);
		return res
			.status(400)
			.send('you need to provide the index of the right answer');
	}

	if (
		!QuestionService.createQuestion(
			theme,
			req.user.id,
			serverUrl + file.path.slice(14, file.path.length),
			description,
			anwserList,
			correctAnswerIndex,
		)
	) {
		fs.unlinkSync(file.path);
		return res.status(500).send('Server error');
	}

	res.status(201).end();
}

//TODO getQuestionById
export async function getQuestionById(req, res) {
	throw new Error('Function not implemented.');
}

//TODO updateQuestionById
export async function updateQuestionById(req, res) {
	throw new Error('Function not implemented.');
}
//TODO deleteQuestionById

export async function deleteQuestionById(req, res) {
	throw new Error('Function not implemented.');
}

//TODO deleteQuestionsByTheme
export async function deleteQuestionsByTheme(req, res) {
	throw new Error('Function not implemented.');
}

export async function getRandomQuestionByTheme(req, res) {
	const theme = await ThemeService.findTheme(
		{ title: req.params.theme },
		'_id',
		1,
	);
	if (!theme)
		return res.status(400).send('You need to provide a valide theme');

	const question = await QuestionService.getRandomQuestionByTheme(
		theme._id,
		req.body.listAnsweredQuestion,
		60,
		3,
	);
	res.send(question);
}

export async function getRandomQuestionsByTheme(req, res) {
	const theme = await ThemeService.findTheme(
		{ title: req.params.theme },
		'_id',
		1,
	);
	if (!theme)
		return res.status(400).send('You need to provide a valide theme');

	const questions = await QuestionService.getRandomQuestionsByTheme(
		theme._id,
		parseInt(req.params.nbQuestion),
		60,
		3,
	);
	res.send(questions);
}
