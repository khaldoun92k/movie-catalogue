import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RatingComponent } from './rating.component';
import { FilmService } from '../service/film-service.service';
import { Film } from '../model/film';
import { of } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';

// Mock data
const mockFilm: Film = {
  filmId: 1,
  title: 'Mock Film',
  genre: 'Mock Genre',
  director: 'Mock Director',
  averageRating: 4.0
};

// Mock service
class MockFilmService {
  rateFilm(film: Film, rating: number) {
    return of(null);
  }
}

describe('RatingComponent', () => {
  let component: RatingComponent;
  let fixture: ComponentFixture<RatingComponent>;
  let filmService: FilmService;
  let dialogRef: MatDialogRef<RatingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RatingComponent ],
      providers: [
        { provide: FilmService, useClass: MockFilmService },
        { provide: MatDialogRef, useValue: { close: jasmine.createSpy('close') } },
        { provide: MAT_DIALOG_DATA, useValue: mockFilm }
      ],
      schemas: [NO_ERRORS_SCHEMA] // To ignore any Angular Material specific issues during this test
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingComponent);
    component = fixture.componentInstance;
    filmService = TestBed.inject(FilmService);
    dialogRef = TestBed.inject(MatDialogRef);

    fixture.detectChanges(); // Trigger initial data binding
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call rateFilm on the service when onClick is called', () => {
    spyOn(filmService, 'rateFilm').and.callThrough();
    component.rating = 5;

    component.onClick();

    expect(filmService.rateFilm).toHaveBeenCalledWith(mockFilm, 5);
    expect(dialogRef.close).toHaveBeenCalledWith({ filmRated: true });
  });

  it('should close the dialog without any data when onNoClick is called', () => {
    component.onNoClick();

    expect(dialogRef.close).toHaveBeenCalledWith();
  });
});
