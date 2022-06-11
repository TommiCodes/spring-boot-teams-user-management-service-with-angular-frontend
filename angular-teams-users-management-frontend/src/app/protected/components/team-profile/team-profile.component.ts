import { UserTeamRelation } from '../../../models/user-team-relation.interfaces';
import { JoinRequestPageResponse as JoinRequestPagedResponse } from '../../../models/join-request.interfaces';
import { JoinRequestService } from '../../services/join-request-service/join-request.service';
import { UserState } from 'src/app/root-states/user.state';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Team } from 'src/app/models/team.interfaces';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Pageable } from 'src/app/models/interfaces';
import { UserTeamRelationPagedResponse } from 'src/app/models/user-team-relation.interfaces';
import {Observable, of, switchMap, tap} from "rxjs";

@Component({
  selector: 'app-team-profile',
  templateUrl: './team-profile.component.html',
  styleUrls: ['./team-profile.component.scss']
})
export class TeamProfileComponent implements OnInit, OnChanges{

  @Input() team!: Team | null;

  @Input() members!: UserTeamRelationPagedResponse | null;
  @Output() paginateMembers: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  @Input() joinRequests!: JoinRequestPagedResponse;
  @Output() paginateJoinRequests: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  teamIdsOfCurrentUser = this.userState.teamIds;
  teamsAuths = this.userState.teamPrivs;
  isTeamAdmin: boolean = false;
  membersDisplayedCols: string[] = ['id', 'email', 'firstname', 'lastname'];
  membersDataSource: MatTableDataSource<UserTeamRelation> = new MatTableDataSource<UserTeamRelation>();

  userHasOpenTeamJoinRequest: boolean = true;

  constructor(private userState: UserState, private joinTeamService: JoinRequestService) { }

  ngOnInit(): void {
    this.isTeamAdmin = this.calcIsTeamAdmin();
    this.joinTeamService.checkIfUserHasOpenJoinRequest(this.team!.id).pipe(
      tap((val: boolean) => this.userHasOpenTeamJoinRequest = val)
    ).subscribe();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['members'].currentValue) {
      this.membersDataSource.data = this.members!._embedded.userTeamRelations;
    }
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginateMembers.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }


  sendJoinRequest() {
    this.joinTeamService.sendJoinRequest(this.team!.id).pipe(
      switchMap(() => this.joinTeamService.checkIfUserHasOpenJoinRequest(this.team!.id).pipe(
        tap((val: boolean) => this.userHasOpenTeamJoinRequest = val)
      ))
    ).subscribe();
  }

  calcIsTeamAdmin() {
    if(!this.teamIdsOfCurrentUser.includes(this.team!.id)) {
      return false;
    }
    const team = this.teamsAuths.find(t => t.teamId === this.team!.id);
    return team!.privileges.includes('ADMIN');
  }

}
