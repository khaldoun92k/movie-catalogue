import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  public loginValid = true;
  username: string;
  password: string;
  redirect(){
    this.router.navigateByUrl('/films');
  }
  

  constructor(private router: Router,private loginService: LoginService) { }

  onLogin(): void {
    
    this.loginService.login(this.username, this.password).subscribe(
      { next:response=>
        {console.log(response);
        this.loginValid = true;
        this.redirect();}, // Here you would typically do something with the received token
        error:err=> 
        {console.error(err);
        this.loginValid = false;
        } // Error handling
      }
    );
  }

}


