import { NgModule, isDevMode } from "@angular/core";
import { MovieTableComponent } from "./movie-table.component";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatTableModule } from "@angular/material/table";
import { MatSortModule } from "@angular/material/sort";
import { MatPaginatorModule } from "@angular/material/paginator";
import { BASE_PATH } from "src/api";
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { MatNativeDateModule } from '@angular/material/core';
import variables from "variables.json";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgFor } from "@angular/common";
import { MatSelectModule } from "@angular/material/select";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from "@angular/material/button";


@NgModule({
    declarations: [
        MovieTableComponent
    ],
    imports: [BrowserModule,
        MatFormFieldModule,
        MatInputModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        NgFor,
        MatDatepickerModule,
        MatNativeDateModule,
        MatButtonModule
    ],
    providers: [provideHttpClient(),
    provideAnimations(),
    importProvidersFrom(MatNativeDateModule),
    { provide: BASE_PATH, useValue: isDevMode() ? variables.development_base_path : variables.production_base_path }
    ],
    bootstrap: [MovieTableComponent]
})
export class MovieTableModule {

}