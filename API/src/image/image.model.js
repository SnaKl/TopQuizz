import { Schema, model } from 'mongoose';

const ImageSchema = new Schema(
	{
		uploadDate: { type: Date, required: true },
		name: { type: String, required: true },
		img: {
			data: { type: Buffer, required: true },
			contentType: { type: String, required: true },
		},
	},
	{
		versionKey: false,
	},
);

export default model('Images', ImageSchema);
