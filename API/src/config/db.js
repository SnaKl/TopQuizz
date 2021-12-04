import mongoose from 'mongoose';

export default {
	mongoose,
	connect: async () => {
		await mongoose.connect(process.env.DATABASE_URL, {
			useNewUrlParser: true,
			useUnifiedTopology: true,
		});
	},
	disconnect: (done) => {
		mongoose.disconnect(done);
	},
};
