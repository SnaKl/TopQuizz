import multer from 'multer';
import path from 'path';
import fs from 'fs';

// fieldname: 'image',
// originalname: 'WIN_20210521_17_23_37_Pro.jpg',
// encoding: '7bit',
// mimetype: 'image/jpeg',
// destination: './src/uploads',
// filename: 'image-1638441661672-915153352.jpg',
// path: 'src\\up
const targets = ['userAvatar', 'themeImage'];

for (const target of targets) {
	let dir = `./public/uploads/${target}`;
	if (!fs.existsSync(dir)) {
		fs.mkdirSync(dir, { recursive: true });
	}
}

export default function uploadImage(objectif) {
	return function (req, res, next) {
		if (!targets.includes(objectif))
			return res.status(400).send('Invalid target');

		const storage = multer.diskStorage({
			destination: (req, file, cb) => {
				cb(null, `./public/uploads/${objectif}`);
			},
			filename: (req, file, cb) => {
				const uniqueSuffix =
					Date.now() + '' + Math.round(Math.random() * 1e9);
				cb(null, uniqueSuffix + path.extname(file.originalname));
			},
		});

		const fileFilter = (req, file, cb) => {
			// reject file
			if (file.mimetype === 'image/jpeg' || file.mimetype === 'image/png')
				cb(null, true);
			cb(null, false);
		};

		const upload = multer({
			storage: storage,
			limits: {
				fileSize: 1024 * 1024 * 5,
			},
			fileFilter: fileFilter,
		});

		const uploadObjectif = upload.single('file');
		uploadObjectif(req, res, function (err) {
			if (err instanceof multer.MulterError) {
				return res.status(400).send(err.message);
			} else if (err) {
				console.log(err);
				return res.status(400).send('Server error');
			}
			next();
		});
	};
}
