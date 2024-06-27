import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmListComponent } from './film-list.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Film } from '../model/film';
import { of } from 'rxjs';
import { FilmService } from '../service/film-service.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

// Mock data
const mockFilms: Film[] = [
  { filmId: 1, title: 'Film1', genre: 'Genre1', director: 'Director1', averageRating: 1 },
  { filmId: 2, title: 'Film2', genre: 'Genre2', director: 'Director2', averageRating: 5 }
];

// Mock service
class MockFilmService {
  findAll() {
    return of(mockFilms);
  }
}
// Mock MatSnackBar
class MockMatSnackBar {
  open = jasmine.createSpy('open');
}

// Mock MatDialog
class MockMatDialog {
  open() {
    return {
      afterClosed: () => of(true)
    };
  }
}

describe('FilmListComponent', () => {
  let component: FilmListComponent;
  let fixture: ComponentFixture<FilmListComponent>;
  let filmService: FilmService;
  let snackBar: MatSnackBar;
  let dialog: MatDialog;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilmListComponent],
      providers: [
        { provide: FilmService, useClass: MockFilmService },
        { provide: MatSnackBar, useClass: MockMatSnackBar },
        { provide: MatDialog, useClass: MockMatDialog }
      ],
      schemas: [NO_ERRORS_SCHEMA] // To ignore any Angular Material specific issues during this test
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilmListComponent);
    component = fixture.componentInstance;
    filmService = TestBed.inject(FilmService);
    snackBar = TestBed.inject(MatSnackBar);
    dialog = TestBed.inject(MatDialog);
    fixture.detectChanges(); // Trigger initial data binding
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load data from the service on init', () => {
    component.ngOnInit();
    fixture.detectChanges();

    expect(component.dataSource.data).toEqual(mockFilms);
  });

  it('should update films when changes', () => {
    const newMockFilms: Film[] = [
      { filmId: 1, title: 'Film1', genre: 'Genre1', director: 'Director1', averageRating: 1 },
      { filmId: 2, title: 'Film2', genre: 'Genre2', director: 'Director2', averageRating: 2 },
      { filmId: 3, title: 'Film3', genre: 'Genre3', director: 'Director3', averageRating: 3 },
      { filmId: 4, title: 'Film4', genre: 'Genre4', director: 'Director4', averageRating: 4 }
    ];

    spyOn(filmService, 'findAll').and.returnValue(of(newMockFilms));

    component.ngOnInit();
    fixture.detectChanges();

    // Since ngOnChanges calls ngOnInit, dataSource should be updated with the service's data, not the newMockFilms
    expect(component.dataSource.data).toEqual(newMockFilms);
  });
});

