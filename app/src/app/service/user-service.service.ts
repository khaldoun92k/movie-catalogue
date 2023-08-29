import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { Observable, map } from 'rxjs';

@Injectable()
export class UserService {

  private usersUrl: string;

  constructor(public http: HttpClient) {
    this.usersUrl = '/users';
  }


  public findAll(): Observable<User[]> {
    return this.http.get<GetUserResponse>(this.usersUrl).pipe(
      map(response => response._embedded?.userList || [])); //?. If _embedded is null or undefined, it won't throw an error
  }                                                         // If userList is undefined or any falsy value, it will return an empty array ([]).

}
interface GetUserResponse {
  _embedded: {
      userList: User[];
      _links: {self: {href: string}};
  };
}