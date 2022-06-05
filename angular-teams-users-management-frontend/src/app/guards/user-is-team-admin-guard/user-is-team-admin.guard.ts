import { UserState } from './../../root-states/user.state';
import { TeamService } from './../../protected/services/team-service/team.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { snackBarConf } from 'src/app/model/consts';

@Injectable({
  providedIn: 'root'
})
export class UserIsTeamAdminGuard implements CanActivate {

  constructor(private userState: UserState, private router: Router, private snack: MatSnackBar) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const teamId = Number(route.paramMap.get('id'));

    if (!this.userState.teamIds.includes(teamId)) {
      this.snack.open(`Missing Persmissions for team, you get redirected to dashboard`, 'Close', snackBarConf);
      return this.router.navigate([state.url, '..']);
    }

    const team = this.userState.teamPrivs.find(t => t.teamId === teamId);
    if (!team) {
      this.snack.open(`Missing Persmissions for team, you get redirected to dashboard`, 'Close', snackBarConf);
      return this.router.navigate([state.url, '..']);
    }

    return team.privileges.includes('ADMIN');
  }

}
