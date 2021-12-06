import User from './user.model';

export async function findUser(query, select = '', limit = 0) {
	const result = await User.find(query, select).limit(limit);
	if (limit === 1) return result[0];
	return result;
}

export function updateUser(query, update, select = '') {
	return User.findOneAndUpdate(query, update, { new: true }).select(select);
}

export function updateUsers(query, update, select = '') {
	return User.updateMany(query, update, { new: true }).select(select);
}
