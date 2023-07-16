import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {AdminComponent} from './components/admin/admin.component';
import {UserPageComponent} from './components/user-page/user-page.component';
import {LoginComponent} from './components/login/login.component';
import {SignupComponent} from './components/signup/signup.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {RequestInterceptor} from "./interceptors/request.interceptor";
import { AddPlaylistComponent } from './components/playlist/add-playlist/add-playlist.component';
import { EditPlaylistComponent } from './components/playlist/edit-playlist/edit-playlist.component';
import { DetailsPlaylistComponent } from './components/playlist/details-playlist/details-playlist.component';
import { AddSongToPlaylistComponent } from './components/playlist/add-song-to-playlist/add-song-to-playlist.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AdminComponent,
    UserPageComponent,
    LoginComponent,
    SignupComponent,
    AddPlaylistComponent,
    EditPlaylistComponent,
    DetailsPlaylistComponent,
    AddSongToPlaylistComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        ReactiveFormsModule,
        HttpClientModule,
        FormsModule,
    ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: RequestInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
