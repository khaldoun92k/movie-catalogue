import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FilmListComponent, NanPipe } from './film-list/film-list.component';
import { UserListComponent } from './user-list/user-list.component';
import { RegisterComponent } from './register/register.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatListModule} from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { provideAnimations } from '@angular/platform-browser/animations';

import { RegisterService } from './service/register.service';
import { FilmService } from './service/film-service.service';
import { UserService } from './service/user-service.service';
import { FilmEditComponent } from './film-edit/film-edit.component';
import { RatingComponent } from './rating/rating.component';
import { MatDialogModule } from '@angular/material/dialog';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    FilmListComponent,
    RegisterComponent,
    UserListComponent,
    FilmEditComponent,
    NanPipe,
    RatingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule, //no routing if not present
    MatToolbarModule, //Matirals...
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    MatListModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  providers: [FilmService,UserService,RegisterService, provideAnimations()],
  bootstrap: [AppComponent]
})
export class AppModule { }
