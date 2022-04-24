import { UserTeam } from './../../../model/user-team.interfaces';
import { JoinRequestPageResponse as JoinRequestPagedResponse } from './../../../model/join-request.interfaces';
import { JoinRequestService } from './../../services/join-request-service/join-request.service';
import { UserState } from 'src/app/root-states/user.state';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Team } from 'src/app/model/team.interfaces';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { User, UserPagedResponse } from 'src/app/model/user.interfaces';
import { Pageable } from 'src/app/model/interfaces';
import { UserTeamPagedResponse } from 'src/app/model/user-team.interfaces';

@Component({
  selector: 'app-team-profile',
  templateUrl: './team-profile.component.html',
  styleUrls: ['./team-profile.component.scss']
})
export class TeamProfileComponent implements OnInit, OnChanges{

  @Input() team!: Team | null;

  @Input() members!: UserTeamPagedResponse | null;
  @Output() paginateMembers: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  @Input() joinRequests!: JoinRequestPagedResponse;
  @Output() paginateJoinRequests: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  teamIdsOfCurrentUser = this.userState.teamIds;
  teamsAuths = this.userState.teamPrivs; 
  isTeamAdmin: boolean = false;
  membersDisplayedCols: string[] = ['id', 'email', 'firstname', 'lastname'];
  membersDataSource: MatTableDataSource<UserTeam> = new MatTableDataSource<UserTeam>();

  constructor(private userState: UserState, private joinTeamService: JoinRequestService) { }

  ngOnInit(): void {
    this.isTeamAdmin = this.calcIsTeamAdmin();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['members'].currentValue) {
      this.membersDataSource.data = this.members!._embedded.userTeams;
    }
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginateMembers.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }


  sendJoinRequest() {
    this.joinTeamService.sendJoinRequest(this.team!.id).subscribe();
  }

  calcIsTeamAdmin() {
    if(!this.teamIdsOfCurrentUser.includes(this.team!.id)) {
      return false;
    }
    const team = this.teamsAuths.find(t => t.teamId === this.team!.id);
    return team!.privileges.includes('ADMIN');
  }

}
