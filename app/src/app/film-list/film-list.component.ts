import { Component, OnInit, Pipe, PipeTransform,ViewChild } from '@angular/core';
import { MatDialog} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Film } from '../model/film';
import { FilmService } from '../service/film-service.service';
import { RatingComponent } from '../rating/rating.component';
import { MatTableDataSource } from '@angular/material/table';
import { RecommendationComponent } from '../recommendation/recommendation.component';


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
  @ViewChild(RecommendationComponent) recommendationComponent: RecommendationComponent;
  dataSource: MatTableDataSource<Film>; //to be able to filter
  displayedColumns: string[] = ['filmId','title','genre','director','AvgRate', 'actions'];
  constructor(private filmService: FilmService,private snackBar: MatSnackBar,public dialog: MatDialog) {
  }

  ngOnInit() {
       
        this.filmService.findAll().subscribe((films) => {
          this.dataSource = new MatTableDataSource(films);
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
        error: (err) => {
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


  //SnackbarStandard function
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

