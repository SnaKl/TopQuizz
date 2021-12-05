export default {
	user: {
		$id: 'userSchema',
		type: 'object',
		properties: {
			nickName: {
				type: 'string',
			},
			email: {
				type: 'string',
			},
			signUpDate: {
				type: 'string',
				format: 'date',
			},
			totalScore: { type: 'number' },
			Score: {
				type: 'array',
			},
		},
		required: ['nickName', 'email', 'signUpDate', 'totalScore', 'Score'],
		additionalProperties: false,
	},
	getUser: {
		$id: 'getUserSchema',
		type: 'object',
		properties: {
			nickName: {
				type: 'string',
			},
			email: {
				type: 'string',
			},
			password: { type: 'string' },
			salt: { type: 'string' },
			signUpDate: {
				type: 'string',
				format: 'date',
			},
			totalScore: { type: 'number' },
			Score: {
				type: 'array',
			},
		},
		required: [
			'nickName',
			'email',
			'password',
			'salt',
			'signUpDate',
			'totalScore',
			'Score',
		],
	},
	getAllUser: {
		$id: 'getAllUserSchema',
		type: 'array',
		items: {
			type: 'object',
			properties: {
				nickName: {
					type: 'string',
				},
				email: {
					type: 'string',
				},
				signUpDate: {
					type: 'string',
					format: 'date',
				},
				totalScore: { type: 'number' },
				Score: {
					type: 'array',
				},
			},
			required: ['nickName', 'signUpDate', 'totalScore', 'Score'],
			additionalProperties: false,
		},
		uniqueItems: true,
	},
};
