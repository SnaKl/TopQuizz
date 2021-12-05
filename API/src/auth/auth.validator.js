import { UserValidator } from '../user/user.validator';

export function loginUser() {
	return [UserValidator.bodyNickname, UserValidator.bodyPassword];
}

// export function connectUser() {}
