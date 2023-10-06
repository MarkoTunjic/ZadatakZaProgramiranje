import { Component, ViewChild } from "@angular/core";
import { MovieDTO } from "src/api";

import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MovieService } from "src/app/services/movie.service";

@Component({
    selector: 'movie-component',
    templateUrl: 'movie-table.component.html',
    standalone: true,
    imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule],
})
export class MovieTableComponent {
    dataSource = new MatTableDataSource<MovieDTO>();
    displayedColumns: string[] = ['id', 'name', 'addingDate', 'genreName'];

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private movieService: MovieService) {
    }

    ngOnInit() {
        this.movieService.getAllMovies().subscribe(movies => this.dataSource = new MatTableDataSource(movies))
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }
}