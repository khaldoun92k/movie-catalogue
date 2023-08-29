import { Component, OnInit } from '@angular/core';
import { Film } from '../model/film';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { map, of, switchMap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-film-edit',
  templateUrl: './film-edit.component.html',
  styleUrls: ['./film-edit.component.css']
})
export class FilmEditComponent  implements OnInit {
  film!: Film;
 

  constructor(private route: ActivatedRoute, private router: Router,
              private http: HttpClient,private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.route.params.pipe(
      map(p => p['id']),
      switchMap(id => {
        if (id === 'new') {
          return of(new Film());
        }
        return this.http.get<Film>(`films/${id}`);
      })
    ).subscribe({
      next: film => {
        this.film = film;

      },
      error: () => {
        this.showSnackbarTopPosition('Error loading','Done','5000','fail-snackbar')
      }
    });
  }

  save() {
    const id = this.film.filmId;
    const method = id ? 'put' : 'post';

    this.http[method]<Film>(`/films${id ? '/' + id : ''}`, this.film).subscribe({
      next: () => {
        this.router.navigate(['/films']);
        this.showSnackbarTopPosition('Saved successfully','Done','5000','success-snackbar');
      },
      error: () => {
        this.showSnackbarTopPosition('Error saving','Done','5000','fail-snackbar');
      }
    });
  }

  async cancel() {
    await this.router.navigate(['/films']);
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