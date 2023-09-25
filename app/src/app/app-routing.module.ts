import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { FilmListComponent } from './film-list/film-list.component';
import { RegisterComponent } from './register/register.component';
import { UserListComponent } from './user-list/user-list.component';
import { FilmEditComponent } from './film-edit/film-edit.component';
import { RecommendationComponent } from './recommendation/recommendation.component';


const routes: Routes = [
  { path: 'film/recommendations', component: RecommendationComponent},
  { path: 'users', component: UserListComponent},
  { path: 'films', component: FilmListComponent},
  { path: 'films/:id',  component: FilmEditComponent},
  { path: 'home', component: HomeComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: '', component: HomeComponent },
  { path: '**', component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
