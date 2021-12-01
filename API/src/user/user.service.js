import User from './user.model';

export async function findUser(query, select = '', limit = 0) {
	const result = await User.find(query).select(select).limit(limit);
	if (limit === 1) return result[0];
	return result;
}
