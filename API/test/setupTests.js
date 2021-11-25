import db from '../src/db';

beforeEach(async () => await db.connect());
afterEach(() => db.disconnect());
