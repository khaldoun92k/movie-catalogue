import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmEditComponent } from './film-edit.component';

describe('FilmEditComponent', () => {
  let component: FilmEditComponent;
  let fixture: ComponentFixture<FilmEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FilmEditComponent]
    });
    fixture = TestBed.createComponent(FilmEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
