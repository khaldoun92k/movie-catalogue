import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  isLoggedIn = new BehaviorSubject<boolean>(false);
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    
    return this.http.post('/login', { username, password }, { responseType: 'text' }).pipe(
      tap(() => {
        // Only set isLoggedIn to true when the request succeeds
        this.isLoggedIn.next(true);
      })
    );
  }
  logout() {
    // logout logic here
    // On logout, set isLoggedIn to false
    this.isLoggedIn.next(false);
  }
}
