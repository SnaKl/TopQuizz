import * as AuthService from './auth.service';
import * as UserService from '../user/user.service';

export async function login(req, res) {
	try {
		const { nickname, password } = req.body;

		const user = await UserService.findUser(
			{ nickname },
			'nickname password salt',
			1,
		);

		if (!user) return res.status(400).send('Invalid Credentials');

		if (!user.validPassword(password))
			return res.status(400).send('Invalid Credentials');

		const token = await AuthService.createToken(user);

		res.status(200).json(token);
	} catch (err) {
		console.log(err);
	}
}
