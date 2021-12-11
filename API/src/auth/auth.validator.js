import { UserValidator } from '../user/user.validator';

export function loginUser() {
	return [UserValidator.queryNickname, UserValidator.queryPassword];
}

// export function connectUser() {}
