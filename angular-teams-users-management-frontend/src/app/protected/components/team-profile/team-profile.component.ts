import { JoinRequest, JoinRequestPageResponse as JoinRequestPagedResponse } from './../../../model/join-request.interfaces';
import { JoinRequestService } from './../../services/join-request-service/join-request.service';
import { UserState } from 'src/app/root-states/user.state';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Team } from 'src/app/model/team.interfaces';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { User, UserPagedResponse } from 'src/app/model/user.interfaces';
import { Pageable } from 'src/app/model/interfaces';

@Component({
  selector: 'app-team-profile',
  templateUrl: './team-profile.component.html',
  styleUrls: ['./team-profile.component.scss']
})
export class TeamProfileComponent implements OnChanges{

  @Input() team!: Team | null;

  @Input() members!: UserPagedResponse | null;
  @Output() paginateMembers: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  @Input() joinRequests!: JoinRequestPagedResponse;
  @Output() paginateJoinRequests: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  teamIdsOfCurrentUser = this.userState.teamIds;
  membersDisplayedCols: string[] = ['id', 'email', 'firstname', 'lastname'];
  membersDataSource: MatTableDataSource<User> = new MatTableDataSource<User>();

  constructor(private userState: UserState, private joinTeamService: JoinRequestService) { }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['members'].currentValue) {
      this.membersDataSource.data = this.members!._embedded.users;
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

}
