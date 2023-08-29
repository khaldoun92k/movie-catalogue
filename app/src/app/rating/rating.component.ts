import { Component, Inject } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import { Film } from '../model/film';
import { FilmService } from '../service/film-service.service';
import { Observable, tap,pipe } from 'rxjs';
@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent {
  rating:number
  
  constructor(
    public dialogRef: MatDialogRef<RatingComponent>,
    private filmService: FilmService,
    @Inject(MAT_DIALOG_DATA) public film: Film,
  ) 
  {
  }

  onClick(): void {     
    this.filmService.rateFilm(this.film, this.rating)
      // Close the dialog and send a response indicating the film was rated
      this.dialogRef.close({ filmRated: true });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
