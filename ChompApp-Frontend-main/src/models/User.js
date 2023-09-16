export default class User {
  constructor(
    firstName,
    lastName,
    email,
    username,
    password,
    gender,
    dateOfBirth,
    roles,
    token,
    userId
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.password = password;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.roles = roles;
    this.token = token;
    this.userId = userId;
  }
}
