import { Component, ViewChild } from "@angular/core";
import { GenreDTO, MovieDTO } from "src/api";

import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MovieService } from "src/app/services/movie.service";
import { Observable } from "rxjs";
import { GenreService } from "src/app/services/genre.service";
import { formatDate } from "@angular/common";

@Component({
    selector: 'movie-component',
    templateUrl: 'movie-table.component.html',
    styleUrls: ['movie-table.component.css']
})
export class MovieTableComponent {
    dateFormat = "dd/MM/yyyy"
    locale = 'en-US';

    dataSource = new MatTableDataSource<MovieDTO>();
    displayedColumns: string[] = ['id', 'name', 'addingDate', 'genreName'];
    options: Observable<GenreDTO[]>;

    searchString: string = ""
    genreNames: string[];
    startDate?: string;
    endDate?: string;

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

    filter() {
        if (this.startDate?.length == 0) {
            this.startDate = undefined;
        }
        if (this.endDate?.length == 0) {
            this.endDate = undefined;
        }
        this.movieService.getFilteredMovies(this.searchString, this.startDate !== undefined ? formatDate(this.startDate, this.dateFormat, this.locale) : this.startDate, this.endDate !== undefined ? formatDate(this.endDate, this.dateFormat, this.locale) : this.endDate, this.genreNames).subscribe(movies => this.dataSource = new MatTableDataSource(movies));
    }
}