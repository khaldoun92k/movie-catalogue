import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Film } from '../model/film';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  private recommendationUrl: string;

  constructor(public http: HttpClient) {
    this.recommendationUrl = '/film/recommendations';
  }

  public findAll(): Observable<Film[]> {
    return this.http.get<GetFilmResponse>(this.recommendationUrl).pipe(
      map(response => response._embedded?.filmList || [])); //?. If _embedded is null or undefined, it won't throw an error
                                                           // If filmList is undefined or any falsy value, it will return an empty array ([]).
}
}
interface GetFilmResponse {
  _embedded: {
      filmList: Film[];
      _links: {self: {href: string}};
  };
}