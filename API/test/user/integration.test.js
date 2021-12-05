import request from 'supertest';
import app from '../../src/app';
const route = '/api/user/';

import { matchers } from 'jest-json-schema';
expect.extend(matchers);

import userSchema from './user.schema';
describe('Test user integration ', () => {
	test('Create user', async () => {
		const response = await request(app).get(route);
		expect(response.statusCode).toBe(201);
		expect(response.body).toMatchSchema(userSchema.user);
		expect(response.body.Score).toBeEmpty();
	});

	test('get user', async () => {
		const nickname = 'test';
		const response = await request(app).get(route + `/${nickname}`);
		expect(response.statusCode).toBe(200);
		expect(response.body).toMatchSchema(userSchema.getUser);
	});

	test('get all users', async () => {
		const response = await request(app).get(route);
		expect(response.statusCode).toBe(200);
		expect(response.body).toBeArray();
		expect(response.body).toMatchSchema(userSchema.getAllUser);
	});

	test('update user', async () => {
		const response = await request(app).get(route);
		expect(response.statusCode).toBe(204);
	});
	test('delete user', async () => {
		const response = await request(app).get(route);
		expect(response.statusCode).toBe(200);
	});
});
