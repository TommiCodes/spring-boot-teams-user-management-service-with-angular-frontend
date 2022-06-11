import { TeamService } from '../../services/team-service/team.service';
import {UserTeamRelation, UserTeamRelationPagedResponse} from '../../../models/user-team-relation.interfaces';
import { ActivatedRoute, Data, Router } from '@angular/router';
import {HandleJoinRequest, JoinRequestPageResponse} from '../../../models/join-request.interfaces';
import {Component, OnInit} from '@angular/core';
import {map, Observable, switchMap, tap} from 'rxjs';
import { Team } from 'src/app/models/team.interfaces';
import { Pageable } from 'src/app/models/interfaces';
import {JoinRequestService} from "../../services/join-request-service/join-request.service";

@Component({
  templateUrl: './team-admin-page.component.html',
  styleUrls: ['./team-admin-page.component.scss']
})
export class TeamAdminPageComponent implements OnInit{

  teamJoinRequests$: Observable<JoinRequestPageResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['joinRequests']));
  team$: Observable<Team> = this.activatedRoute.data.pipe(map((data: Data) => data['team']));
  teamMembers$: Observable<UserTeamRelationPagedResponse> = this.activatedRoute.data.pipe(map((data: Data) => data['teamMembers']));

  team: Team | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private teamService: TeamService,
    private joinRequestService: JoinRequestService) { }

  ngOnInit(): void {
    this.team$.pipe(
      tap((team) => this.team = team)
    ).subscribe();
  }

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

  handleJoinRequest(handleJoinRequest: HandleJoinRequest) {
    this.joinRequestService.handleJoinRequest(handleJoinRequest.joinRequest, handleJoinRequest.updateJoinTeamRequest).pipe(
      // reload page, so that the resolvers gets new data
      tap(() => this.router.navigate([]))
    ).subscribe();
  }

  removeUser(userTeam: UserTeamRelation) {
    this.teamService.removeUserFromTeam(this.team!, userTeam.user).pipe(
      tap(() => this.router.navigate([]))
    ).subscribe();
  }

}
