import { UserState } from './../../root-states/user.state';
import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { LOCALSTORAGE_TOKEN_KEY } from 'src/app/app.module';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {

  isExpanded = false;
  username = this.userState.snapshot.username;

  constructor(
    private router: Router,
    private userState: UserState
  ) {}

  logout() {
    // Removes the jwt token from the local storage, so the user gets logged out & then navigate back to the "public" routes
    localStorage.removeItem(LOCALSTORAGE_TOKEN_KEY);
    this.router.navigate(['../../']);
  }
}
