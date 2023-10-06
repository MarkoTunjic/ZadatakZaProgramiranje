import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { MovieTableComponent } from './app/components/MovieTableComponent/movie-table.component';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { MatNativeDateModule } from '@angular/material/core';




bootstrapApplication(MovieTableComponent, {
  providers: [
    provideHttpClient(),
    provideAnimations(),
    importProvidersFrom(MatNativeDateModule)
  ]
}).catch(err => console.error(err));
