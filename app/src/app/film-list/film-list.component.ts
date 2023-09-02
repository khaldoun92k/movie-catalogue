import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Film } from '../model/film';
import { FilmService } from '../service/film-service.service';
import { forkJoin, map, mergeMap, take } from 'rxjs';
import { RatingComponent } from '../rating/rating.component';
import { MatTableDataSource } from '@angular/material/table';


//pipe to process Not yet rated case avg="Na"
//pipes are a way to write display-value transformations that you can declare in your HTML
@Pipe({
  name: 'nanPipe'
})
export class NanPipe implements PipeTransform {
  transform(value: number): number | string {
    return isNaN(value) ? 'Not yet rated' : value.toFixed(2); //return with 2 decimal
  }
}

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css']
}
)

export class FilmListComponent implements OnInit {

  films: Film[];
  dataSource: MatTableDataSource<Film>; //to be able to filter
  displayedColumns: string[] = ['filmId','title','genre','director','AvgRate', 'actions'];
  constructor(private filmService: FilmService,private snackBar: MatSnackBar,public dialog: MatDialog) {
  }
  /*call the getFilmAverageRating method for every movie to get its avg*/
  ngOnInit() {
        this.filmService.findAll().pipe(
          mergeMap((films) => {
            return forkJoin(
              films.map((film) =>
                this.filmService.getFilmAverageRating(film.filmId).pipe(
                  take(1),
                  map((avgRating) => ({ ...film, rateAvg: avgRating }))
                )
              )
            );
          })
        ).subscribe((filmsWithAvgRating) => {
          this.films = filmsWithAvgRating;
          this.dataSource = new MatTableDataSource(this.films);
        });

  }
  delete(film: Film): void {
    if (confirm(`Are you sure you want to delete ${film.title}?`)) {
      this.filmService.http.delete(`films/${film.filmId}`).subscribe({
        next: () => {
          this.showSnackbarTopPosition('Delete was successful!','Done','5000','success-snackbar');
          setTimeout(() => {
            this.ngOnInit();
          }, 1000);
        },
        error: () => {
          this.showSnackbarTopPosition('Error deleting.','Done','5000','fail-snackbar');
        }
      });
    }
  }

  //Rating dialog
  openDialog(film: Film): void {
    this.dialog.open(RatingComponent, {
      data: film
    }).afterClosed().subscribe(result => {
      this.ngOnInit();
    });
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
    applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
}

