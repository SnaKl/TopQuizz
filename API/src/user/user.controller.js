import fs from 'fs';
import fsPromises from 'fs/promises';

import { serverUrl } from '../server';

import User from './user.model';
import * as UserService from './user.service';

import _ from 'lodash';

export async function getUsers(req, res) {
	const users = await UserService.findUser(
		{},
		'nickname totalScore signUpDate',
	);

	res.json({ users });
}

export async function getUser(req, res) {
	res.send('getUser not implemented');
}
export async function updateUser(req, res) {
	var update = _.pick(req.body, ['email']);

	const newUser = await UserService.updateUser(
		{ nickname: req.user.nickname },
		{ $set: update },
	);

	res.json({ user: newUser });
}

export async function updateUserAvatar(req, res) {
	if (!req.file) return res.status(401).send('You need to provide an image');

	// récupération du fichier télécharger
	let file = req.file;

	// récupération de l'avatar de l'utilisateur
	let user = await UserService.findUser(
		{ nickname: req.user.nickname },
		'avatar',
		1,
	);
	user = user.toObject();

	if (user.avatar) {
		const path =
			'public/uploads' +
			user.avatar.slice(serverUrl.length, user.avatar.length);

		try {
			// supprime l'ancien avatar
			await fsPromises.unlink(path);
		} catch (error) {
			// supprime le fichié téléchargé
			fs.unlinkSync(file.path);
			//suprime
			await UserService.updateUser(
				{ nickname: req.user.nickname },
				{ $unset: { avatar: true } },
			);
			return res.status(500).send('Server error');
		}
	}

	const url = serverUrl + file.path.slice(14, file.path.length);

	const newUser = await UserService.updateUser(
		{ nickname: req.user.nickname },
		{ $set: { avatar: url } },
	);

	res.json({ user: newUser });
}

export async function createUser(req, res) {
	try {
		const { nickname, email, password } = req.body;

		if (await UserService.findUser({ nickname }, 'id', 1))
			return res.status(400).json({ msg: 'Nickname is already used' });

		if (await UserService.findUser({ email }, 'id', 1))
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
	const user = await UserService.findUser(
		{ nickname: req.params.nickname },
		'nickname totalScore signUpDate',
		1,
	);
	res.send({ user });
}

export async function updateUserByNickname(req, res) {
	res.send('updateUserByNickname not implemented');
}

export async function deleteUserByNickname(req, res) {
	res.send('deleteUserByNickname not implemented');
}
