import User from './user.model';

export function findUser(query) {
	return User.findOne(query);
}

export async function findUsers() {
	return User.find({});
}
