import { Component, ViewChild } from "@angular/core";
import { GenreDTO, MovieDTO } from "src/api";
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MovieService } from "src/app/services/movie.service";
import { Observable } from "rxjs";
import { GenreService } from "src/app/services/genre.service";
import { formatDate } from "@angular/common";
import { FormControl } from "@angular/forms";

@Component({
    selector: 'movie-component',
    templateUrl: 'movie-table.component.html',
    styleUrls: ['movie-table.component.css']
})
export class MovieTableComponent {
    dateFormat = "MM/dd/yyyy"
    locale = 'en-US';

    dataSource = new MatTableDataSource<MovieDTO>();
    displayedColumns: string[] = ['id', 'name', 'addingDate', 'genreName'];
    options: Observable<GenreDTO[]>;

    searchString: string = ""
    genreNames: string[];
    startDate?: Date;
    endDate?: Date;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private movieService: MovieService, private genreService: GenreService, private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit() {
        this.route.queryParams
            .subscribe(params => {
                console.log(params); // { order: "popular" }
                this.searchString = params["searchString"];
                this.startDate = params["startDate"] === undefined ? undefined : new Date(params["startDate"]);
                this.endDate = params["endDate"] === undefined ? undefined : new Date(params["endDate"]);
                this.genreNames = typeof params["genreNames"] === "string" ? [params["genreNames"]] : params["genreNames"];
                const formattedStartDate = this.startDate !== undefined ? formatDate(this.startDate, this.dateFormat, this.locale) : this.startDate;
                const formattedEndDate = this.endDate !== undefined ? formatDate(this.endDate, this.dateFormat, this.locale) : this.endDate;
                this.movieService.getFilteredMovies(this.searchString, formattedStartDate, formattedEndDate, this.genreNames).subscribe(movies => {
                    this.dataSource = new MatTableDataSource(movies)
                    this.dataSource.paginator = this.paginator;
                });
            }
            );
        this.options = this.genreService.getAllGenres();
    }

    ngAfterViewInit() {
    }

    filter() {
        const formattedStartDate = this.startDate !== undefined ? formatDate(this.startDate, this.dateFormat, this.locale) : this.startDate;
        const formattedEndDate = this.endDate !== undefined ? formatDate(this.endDate, this.dateFormat, this.locale) : this.endDate;
        const adjustedSearchString = this.searchString?.length == 0 ? undefined : this.searchString;
        this.router.navigate(
            ['/home'],
            {
                queryParams: {
                    searchString: adjustedSearchString,
                    startDate: formattedStartDate,
                    endDate: formattedEndDate,
                    genreNames: this.genreNames
                },
            }
        );
        this.movieService.getFilteredMovies(adjustedSearchString, formattedStartDate, formattedEndDate, this.genreNames).subscribe(movies => {
            this.dataSource = new MatTableDataSource(movies)
            this.dataSource.paginator = this.paginator;
        });
    }
}