import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatTableDataSource } from '@angular/material/table';
import { of } from 'rxjs';
import { RecommendationComponent } from './recommendation.component';
import { RecommendationService } from '../service/recommendation.service';
import { Film } from '../model/film';
import { NO_ERRORS_SCHEMA } from '@angular/core';

// Mock data
const mockFilms: Film[] = [
  { filmId: 1, title: 'Film1', genre: 'Genre1', director: 'Director1', averageRating: 4.5 },
  { filmId: 2, title: 'Film2', genre: 'Genre2', director: 'Director2', averageRating: 3.7 }
];

// Mock service
class MockRecommendationService {
  findAll() {
    return of(mockFilms);
  }
}

describe('RecommendationComponent', () => {
  let component: RecommendationComponent;
  let fixture: ComponentFixture<RecommendationComponent>;
  let recommendationService: RecommendationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecommendationComponent ],
      providers: [
        { provide: RecommendationService, useClass: MockRecommendationService }
      ],
      schemas: [NO_ERRORS_SCHEMA] // To ignore any Angular Material specific issues during this test
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecommendationComponent);
    component = fixture.componentInstance;
    recommendationService = TestBed.inject(RecommendationService);

    // Initialize component with mock data
    component.parentData = new MatTableDataSource(mockFilms);
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

  it('should update dataSource when parentData changes', () => {
    const newMockFilms: Film[] = [
      { filmId: 3, title: 'NewFilm1', genre: 'NewGenre1', director: 'NewDirector1', averageRating: 5 }
    ];

    component.parentData = new MatTableDataSource(newMockFilms);
    component.ngOnChanges({
      parentData: {
        currentValue: component.parentData,
        previousValue: null,
        firstChange: true,
        isFirstChange: () => true
      }
    });

    fixture.detectChanges();

    // Since ngOnChanges calls ngOnInit, dataSource should be updated with the service's data, not the newMockFilms
    expect(component.dataSource.data).toEqual(newMockFilms);
  });
});
