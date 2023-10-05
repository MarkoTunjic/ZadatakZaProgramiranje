export * from './genreController.service';
import { GenreControllerService } from './genreController.service';
export * from './movieController.service';
import { MovieControllerService } from './movieController.service';
export const APIS = [GenreControllerService, MovieControllerService];
