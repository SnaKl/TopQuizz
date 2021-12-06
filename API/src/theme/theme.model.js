import { Schema, model } from 'mongoose';

const ThemeSchema = new Schema(
	{
		title: { type: String, required: true, unique: true },
		imageUrl: { type: String },
		description: { type: String },
	},
	{
		versionKey: false,
	},
);
export default model('Theme', ThemeSchema, 'Theme');
