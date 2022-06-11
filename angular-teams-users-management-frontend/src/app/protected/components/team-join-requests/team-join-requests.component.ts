import {HandleJoinRequest, JoinRequest, JoinStatus} from 'src/app/models/join-request.interfaces';
import { JoinRequestPageResponse, UpdateJoinTeamRequest } from '../../../models/join-request.interfaces';
import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core';
import { Team } from 'src/app/models/team.interfaces';
import { Pageable } from 'src/app/models/interfaces';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-team-join-requests',
  templateUrl: './team-join-requests.component.html',
  styleUrls: ['./team-join-requests.component.scss']
})
export class TeamJoinRequestsComponent implements OnChanges {

  @Input() team!: Team | null;
  @Input() joinRequests!: JoinRequestPageResponse | null;
  @Output() paginateJoinRequests: EventEmitter<Pageable> = new EventEmitter<Pageable>();
  @Output() handleJoinRequestEvent: EventEmitter<HandleJoinRequest> = new EventEmitter<HandleJoinRequest>();

  membersDisplayedCols: string[] = ['id', 'user', 'joinStatus', 'team', 'actions'];
  dataSource: MatTableDataSource<JoinRequest> = new MatTableDataSource<JoinRequest>();

  joinStati = JoinStatus;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['joinRequests'].currentValue) {
      this.dataSource.data = this.joinRequests!._embedded.joinRequests;
    }
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginateJoinRequests.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }

  handleJoinRequest(joinRequest: JoinRequest, joinStatus: JoinStatus) {
    const updateJoinTeamRequest: UpdateJoinTeamRequest = {joinStatus};
    this.handleJoinRequestEvent.emit({updateJoinTeamRequest, joinRequest});
  }

}
