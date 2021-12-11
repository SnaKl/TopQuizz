import Theme from './theme.model';

export async function findTheme(query = {}, select = '', limit = 0) {
	const result = await Theme.find(query, select).limit(limit);
	if (limit === 1) return result[0];
	return result;
}

export async function createTheme(title, description, imageUrl) {
	let theme = new Theme({
		title,
		description,
		imageUrl,
	});

	return await theme.save();
}

export function updateTheme(query, update, select = '') {
	return Theme.findOneAndUpdate(query, update, { new: true }).select(select);
}
