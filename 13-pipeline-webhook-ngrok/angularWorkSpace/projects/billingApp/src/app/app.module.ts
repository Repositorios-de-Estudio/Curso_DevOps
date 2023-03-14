import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ApiModule } from '../services/billing-module/api.module';
import { BASE_PATH } from '../services/billing-module/variables';
import {environment} from '../environments/environment';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    //allow http operations
    HttpClientModule,
    //include Apimodule
    ApiModule,
    //enable template driven
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {provide: BASE_PATH, useValue: environment.basePath}],
  bootstrap: [AppComponent]
})
export class AppModule { 

}
