import { UserState } from './root-states/user.state';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { JwtModule } from '@auth0/angular-jwt';
import { NgxsModule } from '@ngxs/store';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsDataPluginModule } from '@angular-ru/ngxs';
import { NgxsStoragePluginModule } from '@ngxs/storage-plugin';

// specify the key where the token is stored in the local storage
export const LOCALSTORAGE_TOKEN_KEY = 'teams_management_app';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    // Import our Routes for this module
    AppRoutingModule,
    // Angular Material Imports
    MatSnackBarModule,
    // Jwt Helper Module Import
    JwtModule.forRoot({
      config: {
        tokenGetter: (request) => {
          // If we send the login request, then we don't want to add a token from our localstorage (if there still is one from one of the last sessions)
          if (request?.url.includes('login')) {
            return null;
          }
          return localStorage.getItem(LOCALSTORAGE_TOKEN_KEY);
        },
        allowedDomains: ['localhost:3000', 'localhost:8080']
      }
    }),
    NgxsModule.forRoot([UserState]),
    NgxsDataPluginModule.forRoot(),
    NgxsStoragePluginModule.forRoot(),
    NgxsReduxDevtoolsPluginModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
