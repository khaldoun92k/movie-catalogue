import { TestBed } from '@angular/core/testing';

import { RecommendationService } from './recommendation.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Film } from '../model/film';


describe('RecommendationService', () => {
  let service: RecommendationService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RecommendationService]
    });

    service = TestBed.inject(RecommendationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve recommended films from the API via GET', () => {
    const dummyFilms: Film[] = [
      { filmId: 1, title: 'Film1', genre: 'Genre1', director: 'Director1', averageRating: 1 },
      { filmId: 2, title: 'Film2', genre: 'Genre2', director: 'Director2', averageRating: 5 }
    ];

    const mockResponse = {
      _embedded: {
        filmList: dummyFilms,
        _links: { self: { href: '/film/recommendations' } }
      }
    };

    service.findAll().subscribe(films => {
      expect(films.length).toBe(2);
      expect(films).toEqual(dummyFilms);
    });

    const req = httpMock.expectOne('/film/recommendations');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should return an empty array if _embedded or filmList is missing', () => {
    const mockResponse = {};

    service.findAll().subscribe(films => {
      expect(films.length).toBe(0);
      expect(films).toEqual([]);
    });

    const req = httpMock.expectOne('/film/recommendations');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });
});