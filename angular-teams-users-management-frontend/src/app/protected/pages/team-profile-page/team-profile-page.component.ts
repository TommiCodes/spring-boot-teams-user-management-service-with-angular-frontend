import { UserPagedResponse } from './../../../model/user.interfaces';
import { Team } from './../../../model/team.interfaces';
import { Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Data, ActivatedRoute, Router } from '@angular/router';
import { Pageable } from 'src/app/model/interfaces';
import { UserTeamPagedResponse } from 'src/app/model/user-team.interfaces';

@Component({
  selector: 'app-team-profile-page',
  templateUrl: './team-profile-page.component.html',
  styleUrls: ['./team-profile-page.component.scss']
})
export class TeamProfilePageComponent {

  team$: Observable<Team> = this.activatedRoute.data.pipe(map((data: Data) => data['team']));
  teamMembers$: Observable<UserTeamPagedResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['teamMembers']));

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  paginate(pageable: Pageable) {
    // If the query Params change, then our resolver will run again and load the new Team Members
    // here we reload the same url but with different query Params
    this.router.navigate([], {
      queryParams: {
        pageSize: pageable.size,
        pageNumber: pageable.number
      }
    });
  }

}
