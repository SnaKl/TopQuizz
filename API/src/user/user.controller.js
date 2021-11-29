import User from './user.model';
import * as UserService from './user.service';

export async function getUsers(req, res) {
	const users = await UserService.findUsers();
	res.send(users);
}

export async function getUser(req, res) {
	res.send('getUser not implemented');
}
export async function updateUser(req, res) {
	res.send('updateUser not implemented');
}

export async function createUser(req, res) {
	try {
		const { nickname, email, password } = req.body;

		if (await UserService.findUser({ nickname }))
			return res.status(400).json({ msg: 'Nickname is already used' });

		if (await UserService.findUser({ email }))
			return res.status(400).json({ msg: 'Email is already used' });

		let user = new User({
			nickname,
			email,
		});

		user.setPassword(password);

		await user.save();

		res.status(201).end();
	} catch (e) {
		console.error(e);
		res.status(500).send('Server error');
	}
}

export async function deleteUser(req, res) {
	res.send('deleteUser not implemented');
}

export async function getUserByNickname(req, res) {
	const user = await UserService.findUser({ nickname: req.params.nickname });
	res.send(user);
}

export async function updateUserByNickname(req, res) {
	res.send('updateUserByNickname not implemented');
}

export async function deleteUserByNickname(req, res) {
	res.send('deleteUserByNickname not implemented');
}
