import { Routes } from '@angular/router';

import { MovieTableComponent } from './components/MovieTableComponent/movie-table.component';

export const appRoutes: Routes = [
    { path: 'home', component: MovieTableComponent },
    { path: '', redirectTo: 'home', pathMatch: 'full' }
];