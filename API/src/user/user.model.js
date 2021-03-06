import { Schema, model } from 'mongoose';
import { randomBytes, pbkdf2Sync } from 'crypto';
const ObjectId = Schema.Types.ObjectId;

const UserSchema = new Schema(
	{
		nickname: { type: String, required: true, unique: true },
		password: { type: String, select: false, required: true },
		salt: { type: String, select: false },
		jwtToken: String,
		email: { type: String, required: true, unique: true },
		totalScore: { type: Number, default: 0 },
		signUpDate: { type: Date, default: Date.now },
		Score: [
			{
				_themeID: { type: ObjectId, ref: 'Theme' },
				points: Number,
			},
		],
		avatar: String,
	},
	{
		versionKey: false,
	},
);

// Method to set salt and hash the password for a user
UserSchema.methods.setPassword = function (string) {
	// Creating a unique salt for a particular user
	this.salt = randomBytes(16).toString('hex');

	// Hashing user's salt and password with 1000 iterations,
	this.password = pbkdf2Sync(string, this.salt, 1000, 64, 'sha512').toString(
		'hex',
	);
};

// Method to check the entered password is correct or not
UserSchema.methods.validPassword = function (string) {
	let password = pbkdf2Sync(string, this.salt, 1000, 64, 'sha512').toString(
		'hex',
	);

	return this.password === password;
};

UserSchema.methods.setTotalScore = function () {
	var totalScore = 0;

	for (const score of this.Score) {
		totalScore += score.points;
	}

	return totalScore;
};

export default model('User', UserSchema, 'User');
