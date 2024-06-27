import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoginService } from './service/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Movie-catalogue';
  isLoggedIn: boolean;
  loginSub: Subscription;

  constructor(private loginservice: LoginService) { }

  ngOnInit() {
    this.loginSub = this.loginservice.isLoggedIn.subscribe(
      (loggedIn) => {
        this.isLoggedIn = loggedIn;
      }
    );
  }

  ngOnDestroy() {
    this.loginSub.unsubscribe();
  }
  
}
