import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ApiModule } from '../services/swaggerbillingAPI/api.module';
import { BASE_PATH } from '../services/swaggerbillingAPI/variables';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [ 
    BrowserModule,
    HttpClientModule,
    ApiModule,
    FormsModule,
    ReactiveFormsModule],
  providers: [
    {
      provide: BASE_PATH, useValue: environment.basePath
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
