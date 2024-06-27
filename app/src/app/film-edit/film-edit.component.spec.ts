import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FilmEditComponent } from './film-edit.component';
import { of } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

const mockActivatedRoute = {
  params: of({ id: 'new' }) // Simulate navigating to a new film creation page
};

const mockRouter = {
  navigate: jasmine.createSpy('navigate')
};

describe('FilmEditComponent', () => {
  let component: FilmEditComponent;
  let fixture: ComponentFixture<FilmEditComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilmEditComponent ],
      imports: [ HttpClientTestingModule,
         MatSnackBarModule ,
         MatFormFieldModule,
         MatInputModule,
         BrowserAnimationsModule,
         MatCardModule,
         FormsModule,
         BrowserAnimationsModule],
      providers: [
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FilmEditComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Ensure no outstanding HTTP requests
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
