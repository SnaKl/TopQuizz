import { Schema, model } from 'mongoose';
const ObjectId = Schema.Types.ObjectId;

const QuestionSchema = new Schema(
	{
		_themeID: { type: ObjectId, ref: 'Theme' },
		_imageID: { type: ObjectId, ref: 'Image' },
		description: { type: String },
		_createdBy: { type: ObjectId, ref: 'User' },
		answerList: ['String'],
		Validation: {
			totalUpVote: 'Integer',
			upVoteByUsers: ['ObjectId'],
			totalDownVote: 'Integer',
			downVoteByUsers: ['ObjectId'],
		},
	},
	{
		versionKey: false,
	},
);
export default model('Question', QuestionSchema);
