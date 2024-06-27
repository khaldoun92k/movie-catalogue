import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Film } from '../model/film';
import { RecommendationService } from '../service/recommendation.service';

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.css']
})
export class RecommendationComponent implements OnInit,OnChanges{
  @Input() parentData: MatTableDataSource<Film>;
  dataSource: MatTableDataSource<Film>; //to be able to filter
  displayedColumns: string[] = ['title','genre','director'];
  constructor(private recommendationService: RecommendationService) {
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['parentData']) {
      this.dataSource = this.parentData;
    }
  }

  ngOnInit() {
        this.recommendationService.findAll().subscribe((films) => {
          this.dataSource = new MatTableDataSource(films);
        });
  }

}
