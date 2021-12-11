import Question from './question.model';

const project = {
	_createdBy: 1,
	imageUrl: 1,
	description: 1,
	answerList: 1,
	correctAnswerIndex: 1,
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

export async function createQuestion(
	theme,
	_createdBy,
	imageUrl,
	description,
	answerList,
	correctAnswerIndex,
) {
	theme.addQuestion();

	let question = new Question({
		_themeID: theme._id,
		_createdBy,
		imageUrl,
		description,
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
		{ $sample: { size: nbQuestion } },
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
