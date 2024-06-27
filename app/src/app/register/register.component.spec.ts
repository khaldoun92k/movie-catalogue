import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RegisterComponent } from './register.component';
import { RegisterService } from '../service/register.service';
import { User } from '../model/user';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule,
                RouterTestingModule,
                MatSnackBarModule,
                MatCardModule,
                MatFormFieldModule,
                FormsModule,
                MatInputModule,
                BrowserAnimationsModule,
                MatToolbarModule],
      declarations: [RegisterComponent],
      providers: [{ provide: RegisterService, useValue: {} }] // Mocking RegisterService
    })
   .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.form.controls['username'].value).toBe('');
    expect(component.form.controls['password'].value).toBe('');
  });
});
