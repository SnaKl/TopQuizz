import jwt from 'jsonwebtoken';
export async function createToken(user) {
	// Create token
	const token = jwt.sign(
		{ user_id: user._id, nickname: user.nickname },
		process.env.TOKEN_KEY,
		{
			expiresIn: '2h',
		},
	);

	return token;
}
