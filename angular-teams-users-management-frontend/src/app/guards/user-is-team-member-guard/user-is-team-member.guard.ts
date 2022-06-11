import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { UserState } from 'src/app/root-states/user.state';

@Injectable({
  providedIn: 'root'
})
export class UserIsTeamMemberGuard implements CanActivate {

  constructor(private userState: UserState) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const teamId = Number(route.paramMap.get('id'));

    if (!this.userState.teamIds.includes(teamId)) {
      // TODO: display Message if false
      return false;
    }

    const team = this.userState.teamPrivs.find(t => t.teamId === teamId);
    // TODO: display Message if false
    return team!.privileges.includes('MEMBER');
  }

}
