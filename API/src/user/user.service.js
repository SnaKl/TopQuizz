import User from './user.model';

export function findUser(query, select = false) {
	if (select) return User.findOne(query).select(select);
	return User.findOne(query).select;
}

export async function findUsers() {
	return User.find({});
}
