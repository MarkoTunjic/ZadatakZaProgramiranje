import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { GenreControllerService, GenreDTO } from "src/api";

@Injectable({
    providedIn: 'root',
})
export class GenreService {
    constructor(private api: GenreControllerService) { }

    getAllGenres(): Observable<Array<GenreDTO>> {
        return this.api.getAllGenres()
    }
}