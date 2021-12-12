import { Schema, model } from 'mongoose';

const ThemeSchema = new Schema(
	{
		title: { type: String, required: true, unique: true },
		imageUrl: { type: String },
		description: { type: String },
		nbQuestion: { type: Number, default: 0 },
	},
	{
		versionKey: false,
	},
);

ThemeSchema.methods.addQuestion = function () {
	this.nbQuestion += 1;
	this.save();
};

export default model('Theme', ThemeSchema, 'Theme');
