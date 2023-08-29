export class User {
	userId: number;
	username: string;
	password: string
	createdAt:Date;
	constructor(user: Partial<User> = {}) {
	  this.username = user.username;
	  this.password = user.password;
	}
}
