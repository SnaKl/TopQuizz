import { Schema, model } from 'mongoose';
const ObjectId = Schema.Types.ObjectId;

const QuestionSchema = new Schema(
	{
		_themeID: { type: ObjectId, ref: 'Theme' },
		_imageID: { type: ObjectId, ref: 'Image' },
		description: { type: String },
		_createdBy: { type: ObjectId, ref: 'User' },
		answerList: [{ type: String, required: true }],
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
export default model('Question', QuestionSchema);
