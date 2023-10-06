import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MovieControllerService, MovieDTO } from "src/api";

@Injectable({
    providedIn: 'root',
})
export class MovieService {
    constructor(private api: MovieControllerService) { }

    getAllMovies(): Observable<Array<MovieDTO>> {
        return this.api.getAllMovies()
    }
    getFilteredMovies(movieName?: string, startDate?: string, endDate?: string, genreNames?: Array<string>): Observable<Array<MovieDTO>> {
        return this.api.getFilteredMovies(movieName, startDate, endDate, genreNames)
    }
}