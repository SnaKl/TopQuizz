import Question from './question.model';

const project = {
	_createdBy: 1,
	imageUrl: 1,
	description: 1,
	anwserList: 1,
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
	_themeID,
	_createdBy,
	imageUrl,
	description,
	anwserList,
	correctAnswerIndex,
) {
	let question = new Question({
		_themeID,
		_createdBy,
		imageUrl,
		description,
		anwserList,
		correctAnswerIndex,
	});

	return await question.save();
}

export async function getRandomQuestionsByTheme(
	themeID,
	nbQuestion,
	percentage,
) {
	return Question.aggregate([
		{ $sample: { size: nbQuestion } },
		{ $match: { _themeID: themeID } },
		{
			$project: project,
		},
		{ $match: { 'Vote.result': { $gte: percentage } } },
	]);
}

export async function getRandomQuestionByTheme(
	themeID,
	questionsID,
	percentage,
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
			{ $match: { 'Vote.result': { $gte: percentage } } },
			{ $sample: { size: 1 } },
		])
	)[0];
}
