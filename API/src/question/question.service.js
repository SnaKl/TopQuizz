import { query } from 'express';
import Question from './question.model';

const project = {
	_createdBy: 1,
	imageUrl: 1,
	description: 1,
	answerList: 1,
	correctAnswerIndex: 1,
	questionTitle: 1,
	Vote: {
		$let: {
			vars: {
				sumVotes: {
					$add: [
						'$Validation.totalDownVote',
						'$Validation.totalUpVote',
					],
				},
			},
			in: {
				total_vote: '$$sumVotes',
				result: {
					$cond: [
						{ $eq: ['$$sumVotes', 0] },
						0,
						{
							$multiply: [
								100,
								{
									$divide: [
										'$Validation.totalUpVote',
										'$$sumVotes',
									],
								},
							],
						},
					],
				},
			},
		},
	},
};

export async function findQuestion(query = {}, select = '', limit = 0) {
	const result = await Question.find(query, select).limit(limit);
	if (limit === 1) return result[0];
	return result;
}

export function countQuestionToValidate(query) {
	return Question.count(query);
}

export async function createQuestion(
	theme,
	_createdBy,
	description,
	questionTitle,
	answerList,
	correctAnswerIndex,
	imageUrl,
) {
	theme.addQuestion();

	let question = new Question({
		_themeID: theme._id,
		_createdBy,
		imageUrl,
		description,
		questionTitle,
		answerList,
		correctAnswerIndex,
	});

	return await question.save();
}

export async function getRandomQuestionsByTheme(
	themeID,
	nbQuestion,
	percentage,
	minNbVote,
) {
	return Question.aggregate([
		{ $match: { _themeID: themeID } },
		{
			$project: project,
		},
		{
			$match: {
				'Vote.total_vote': { $gte: minNbVote },
				'Vote.result': { $gte: percentage },
			},
		},
		{ $sample: { size: nbQuestion } },
	]);
}

export async function getRandomQuestionByTheme(
	themeID,
	questionsID,
	percentage,
	minNbVote,
) {
	const filter = {
		_themeID: themeID,
		_id: { $nin: questionsID },
	};
	return (
		await Question.aggregate([
			{ $match: filter },
			{
				$project: project,
			},
			{
				$match: {
					'Vote.total_vote': { $gte: minNbVote },
					'Vote.result': { $gte: percentage },
				},
			},
			{ $sample: { size: 1 } },
		])
	)[0];
}
