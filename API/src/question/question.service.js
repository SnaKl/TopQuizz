import Question from './question.model';

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
//TODO system renvoyer uniquement les upvote question
export async function getRandomQuestionsByTheme(themeID, nbQuestion) {
	const filter = {
		_themeID: themeID,
	};
	return Question.aggregate([
		{ $sample: { size: nbQuestion } },
		{ $match: filter },
		{
			$project: {
				_createdBy: 1,
				imageUrl: 1,
				description: 1,
				anwserList: 1,
				correctAnswerIndex: 1,
			},
		},
	]);
}

export async function getRandomQuestionByTheme(themeID, questionsID) {
	const filter = {
		_themeID: themeID,
		_id: { $nin: questionsID },
	};
	return await Question.aggregate([
		{ $sample: { size: 1 } },
		{ $match: filter },
		{
			$project: {
				_createdBy: 1,
				imageUrl: 1,
				description: 1,
				anwserList: 1,
				correctAnswerIndex: 1,
			},
		},
	]);
}
