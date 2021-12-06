import db from '../src/config/db';

beforeEach(async () => await db.connect());
afterEach(() => db.disconnect());
