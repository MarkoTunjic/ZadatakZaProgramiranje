import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ApiModule, BASE_PATH } from 'src/api';
import { HttpClientModule } from '@angular/common/http';
import variables from "variables.json";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ApiModule,
    HttpClientModule
  ],
  providers: [{ provide: BASE_PATH, useValue: isDevMode() ? variables.development_base_path : variables.production_base_path }],
  bootstrap: [AppComponent]
})
export class AppModule { }
