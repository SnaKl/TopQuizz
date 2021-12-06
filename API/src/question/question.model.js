import { Schema, model } from 'mongoose';
const ObjectId = Schema.Types.ObjectId;

const QuestionSchema = new Schema(
	{
		_themeID: { type: ObjectId, ref: 'Theme' },
		_createdBy: { type: ObjectId, ref: 'User' },
		imageUrl: String,
		description: String,
		anwserList: [String],
		correctAnswerIndex: { type: Number, min: 0, max: 3 },
		Validation: {
			totalUpVote: { type: Number, default: 0 },
			upVoteByUsers: [{ type: ObjectId, ref: 'User' }],
			totalDownVote: { type: Number, default: 0 },
			downVoteByUsers: [{ type: ObjectId, ref: 'User' }],
		},
	},
	{
		versionKey: false,
	},
);
export default model('Question', QuestionSchema, 'Question');
