import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../service/login.service';
import { RouterTestingModule } from '@angular/router/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      providers: [
        { provide: Router, useValue: {} }, 
        { provide: LoginService, useValue: {} } 
      ],
      imports: [
        RouterTestingModule,  
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        BrowserAnimationsModule,
        RouterModule,
        FormsModule  ] 
    })
   .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should have Router and LoginService injected', () => {
    expect(component['router']).toBeDefined();
    expect(component['loginService']).toBeDefined();
  });
});
