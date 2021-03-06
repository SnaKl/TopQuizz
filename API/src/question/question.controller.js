import fs from 'fs';

import * as QuestionService from './question.service';
import * as ThemeService from '../theme/theme.service';

export async function createQuestion(req, res) {
	const file = req.file;

	var {
		themeTitle,
		description,
		questionTitle,
		answerList,
		correctAnswerIndex,
	} = req.body;

	if (!themeTitle) {
		if (file) fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a theme');
	}

	const theme = await ThemeService.findTheme(
		{ title: themeTitle },
		'_id nbQuestion',
		1,
	);
	if (!theme) {
		if (file) fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a valide theme');
	}

	if (!description) {
		if (file) fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a description');
	}

	if (!questionTitle) {
		if (file) fs.unlinkSync(file.path);
		return res.status(400).send('you need to provide a question title');
	}

	if (!answerList) {
		if (file) fs.unlinkSync(file.path);
		return res
			.status(400)
			.send(
				'you need to provide a list of answer of this format : answer1///answer2///answer3///answer4',
			);
	}

	answerList = answerList.split('///');
	if (answerList.length !== 4) {
		if (file) fs.unlinkSync(file.path);
		return res
			.status(400)
			.send(
				'you need to provide a list of answer of this format : answer1///answer2///answer3///answer4',
			);
	}
	correctAnswerIndex = parseInt(correctAnswerIndex);
	if (correctAnswerIndex < 0 || correctAnswerIndex > 3) {
		if (file) fs.unlinkSync(file.path);
		return res
			.status(400)
			.send('you need to provide the index of the right answer');
	}

	const path = file != null ? file.path.slice(14, file.path.length) : null;
	if (
		!QuestionService.createQuestion(
			theme,
			req.user.id,
			description,
			questionTitle,
			answerList,
			correctAnswerIndex,
			path,
		)
	) {
		if (file) fs.unlinkSync(file.path);
		return res.status(500).send('Server error');
	}

	res.status(201).end();
}

export async function putVoteQuestionById(req, res) {
	const { vote } = req.body;
	const { questionId } = req.params;
	const userId = req.user.id;

	var question = await QuestionService.findQuestion(
		{
			_id: questionId,
			_createdBy: { $ne: userId },
			'Validation.upVoteByUsers': { $ne: userId },
			'Validation.downVoteByUsers': { $ne: userId },
		},
		'',
		1,
	);

	if (!question) return res.status(400).end();

	if (vote === 'upVote') {
		question.Validation.upVoteByUsers.push(userId);
		question.Validation.totalUpVote += 1;
	} else {
		question.Validation.downVoteByUsers.push(userId);
		question.Validation.totalDownVote += 1;
	}
	question.save();

	res.json(question);
}

export async function getQuestionToValidate(req, res) {
	const userId = req.user.id;

	const theme = await ThemeService.findTheme(
		{ title: req.params.theme },
		'_id',
		1,
	);

	if (!theme)
		return res.status(400).send('You need to provide a valide theme');

	const question = await QuestionService.findQuestion(
		{
			_themeID: theme._id,
			_createdBy: { $ne: userId },
			'Validation.upVoteByUsers': { $ne: userId },
			'Validation.downVoteByUsers': { $ne: userId },
		},
		'',
		1,
	);

	res.json({ question });
}

export async function getQuestionNbToValidate(req, res) {
	const userId = req.user.id;

	const theme = await ThemeService.findTheme(
		{ title: req.params.theme },
		'_id',
		1,
	);

	if (!theme)
		return res.status(400).send('You need to provide a valide theme');

	const questionNb = await QuestionService.countQuestionToValidate({
		_themeID: theme._id,
		_createdBy: { $ne: userId },
		'Validation.upVoteByUsers': { $ne: userId },
		'Validation.downVoteByUsers': { $ne: userId },
	});

	res.json({ questionNb });
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
	res.send({ question });
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
	res.send({ questions });
}
