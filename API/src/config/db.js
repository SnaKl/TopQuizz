import mongoose from 'mongoose';
import dotenv from 'dotenv';
dotenv.config();

export default {
	mongoose,
	connect: async () => {
		mongoose.Promise = Promise;
		await mongoose.connect(process.env.DATABASE_URL, {
			useNewUrlParser: true,
			useUnifiedTopology: true,
		});
	},
	disconnect: (done) => {
		mongoose.disconnect(done);
	},
};
