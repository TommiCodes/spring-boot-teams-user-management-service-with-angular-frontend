import { TeamService } from './../../services/team-service/team.service';
import { UserTeamPagedResponse } from './../../../model/user-team.interfaces';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { JoinRequestPageResponse } from '../../../model/join-request.interfaces';
import { Component } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Team } from 'src/app/model/team.interfaces';
import { Pageable } from 'src/app/model/interfaces';

@Component({
  selector: 'app-team-admin-page',
  templateUrl: './team-admin-page.component.html',
  styleUrls: ['./team-admin-page.component.scss']
})
export class TeamAdminPageComponent {

  teamJoinRequests$: Observable<JoinRequestPageResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['joinRequests']));
  team$: Observable<Team> = this.activatedRoute.data.pipe(map((data: Data) => data['team']));
  teamMembers$: Observable<UserTeamPagedResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['teamMembers']));

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private teamService: TeamService) { }

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
