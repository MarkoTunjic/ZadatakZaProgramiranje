import { Component, ViewChild } from "@angular/core";
import { GenreDTO, MovieDTO } from "src/api";

import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MovieService } from "src/app/services/movie.service";
import { FormControl } from "@angular/forms";
import { Observable } from "rxjs";
import { map, startWith } from 'rxjs/operators';
import { GenreService } from "src/app/services/genre.service";

@Component({
    selector: 'movie-component',
    templateUrl: 'movie-table.component.html',
})
export class MovieTableComponent {
    dataSource = new MatTableDataSource<MovieDTO>();
    displayedColumns: string[] = ['id', 'name', 'addingDate', 'genreName'];
    searchString: string = ""
    options: Observable<GenreDTO[]>;
    selected: GenreDTO[];
    startDate: string;
    endDate: string;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private movieService: MovieService, private genreService: GenreService) {
    }

    ngOnInit() {
        this.movieService.getAllMovies().subscribe(movies => this.dataSource = new MatTableDataSource(movies));
        this.options = this.genreService.getAllGenres();
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    searchStringChange() {
        console.log(this.startDate)
    }
}