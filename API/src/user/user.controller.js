import User from './user.model';
import * as UserService from './user.service';

export async function getUsers(req, res) {
	const users = await UserService.findUsers();
	res.send(users);
}

export async function getUser(req, res) {
	res.send('get user');
}
export async function updateUser(req, res) {
	res.send('TEST');
}

export async function createUser(req, res) {
	res.send('TEST');
}

export async function deleteUser(req, res) {
	res.send('TEST');
}

export async function getUserByNickname(req, res) {
	const user = await UserService.findUser({ nickname: req.params.nickname });
	res.send(user);
}

export async function updateUserByNickname(req, res) {
	res.send('TEST');
}

export async function deleteUserByNickname(req, res) {
	res.send('TEST');
}
