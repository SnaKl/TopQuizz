import { validationResult } from 'express-validator';
export default function validate(req, res, next) {
	const errors = validationResult(req);
	if (!errors.isEmpty()) {
		const extractedErrors = new Object();

		errors.array().map((err) => {
			if (!extractedErrors[err.param]) extractedErrors[err.param] = [];
			extractedErrors[err.param].push(err.msg);
		});

		return res.status(400).json({
			errors: extractedErrors,
		});
	}

	next();
}
