import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { MovieTableModule } from './app/components/MovieTableComponent/movie-table.module';

platformBrowserDynamic().bootstrapModule(MovieTableModule)
  .catch(err => console.error(err));
