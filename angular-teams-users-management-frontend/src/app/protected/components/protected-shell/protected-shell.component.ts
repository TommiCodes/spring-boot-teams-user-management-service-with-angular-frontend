import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LOCALSTORAGE_TOKEN_KEY } from 'src/app/app.module';
import { UserState } from 'src/app/root-states/user.state';

@Component({
  selector: 'app-protected-shell',
  templateUrl: './protected-shell.component.html',
  styleUrls: ['./protected-shell.component.scss'],
})
export class ProtectedShellComponent {
  isExpanded = false;
  username = this.userState.snapshot.username;

  constructor(private router: Router, private userState: UserState) {}

  logout() {
    // Removes the jwt token from the local storage, so the user gets logged out & then navigate back to the "public" routes
    localStorage.removeItem(LOCALSTORAGE_TOKEN_KEY);
    this.router.navigate(['../../']);
  }
}
