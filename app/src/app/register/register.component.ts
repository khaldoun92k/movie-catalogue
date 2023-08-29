import { Component,inject } from '@angular/core';
import { RegisterService } from '../service/register.service';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  //user: User
  form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
  });
  public registerValid = true;
  constructor(private router: Router,private registerService: RegisterService,private snackBar: MatSnackBar) { }

  onRegister(): void {  

    let user = new User({ username: this.form.get('username').value, password: this.form.get('password').value });
   
    this.registerService.register(user).subscribe(
      { next:response=>
        {
        console.log(response);
        this.registerValid = true;
        this.showSnackbarTopPosition('Registration done !','Done','5000','success-snackbar')
        }, // Here you would typically do something with the received token
        error:err=> 
        {console.error(err);
        this.registerValid = false;
        this.showSnackbarTopPosition('Registration error !','Done','5000','fail-snackbar')
        } // Error handling
      }
    );
  }
  //snackbarStandard function
  showSnackbarTopPosition(content, action, duration,color) {
    this.snackBar.open(content, action, {
      duration: duration,
      verticalPosition: "top", // Allowed values are  'top' | 'bottom'
      horizontalPosition: "right", // Allowed values are 'start' | 'center' | 'end' | 'left' | 'right'
      panelClass: [color] 
    });
  }
}

