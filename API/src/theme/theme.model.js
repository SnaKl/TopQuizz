import { Schema, model } from 'mongoose';
const ObjectId = Schema.Types.ObjectId;

const ThemeSchema = new Schema(
	{
		_imageID: { type: ObjectId, ref: 'Image' },
		title: { type: String, required: true, unique: true },
		description: { type: String },
		questionNB: { type: Number, default: 0 },
	},
	{
		versionKey: false,
	},
);
export default model('Theme', ThemeSchema);
