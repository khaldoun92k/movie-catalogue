import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Film } from '../model/film';
import { Observable, Subscriber, map, tap } from 'rxjs';

@Injectable()
export class FilmService {

  private filmsUrl: string;
  private ratingUrl: string;

  constructor(public http: HttpClient) {
    this.filmsUrl = '/films';
    this.ratingUrl= '/rating';
  }



  public findAll(): Observable<Film[]> {
    return this.http.get<GetFilmResponse>(this.filmsUrl).pipe(
      map(response => response._embedded?.filmList || [])); //?. If _embedded is null or undefined, it won't throw an error
  }                                                         // If filmList is undefined or any falsy value, it will return an empty array ([]).


  public rateFilm(film: Film, rating: number): void {
    let filmId= film.filmId;
    const params = {
      filmId: filmId.toString(),
      rating: rating.toString()
    };
     this.http.post(this.ratingUrl,params, { responseType: 'text' }).
      subscribe({
       next: ()  => {
      console.log("Movie : "+film.filmId+" has been rated : "+rating);
      },
      error:error =>console.log(error)

    });
  }

}
interface FilmContainer {
  film: Film;
}
interface GetFilmResponse {
  _embedded: {
      filmList: Film[];
      _links: {self: {href: string}};
  };
}