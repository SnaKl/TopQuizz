import jwt from 'jsonwebtoken';

const config = process.env;

export default function (req, res, next) {
	let token = req.headers['x-access-token'] || req.headers['authorization'];

	if (!token || !token.startsWith('Bearer '))
		return res
			.status(403)
			.send('A valid token is required for authentication');

	token = token.slice(7, token.length);

	try {
		const decoded = jwt.verify(token, config.TOKEN_KEY);
		req.user = decoded;
	} catch (err) {
		return res.status(401).send('Invalid Token');
	}

	next();
}
