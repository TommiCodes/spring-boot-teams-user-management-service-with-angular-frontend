import { JoinRequestService } from '../../services/join-request-service/join-request.service';
import { JoinRequest, JoinStatus } from 'src/app/model/join-request.interfaces';
import { JoinRequestPageResponse, UpdateJoinTeamRequest } from '../../../model/join-request.interfaces';
import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { Team } from 'src/app/model/team.interfaces';
import { Pageable } from 'src/app/model/interfaces';
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

  membersDisplayedCols: string[] = ['id', 'user', 'joinStatus', 'team', 'actions'];
  dataSource: MatTableDataSource<JoinRequest> = new MatTableDataSource<JoinRequest>();

  constructor(private joinRequestService: JoinRequestService) {}

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

  accept(joinRequest: JoinRequest) {
    const updateJoinRequest: UpdateJoinTeamRequest = {
      joinStatus: JoinStatus.ACCEPTED
    };
    this.joinRequestService.handleJoinRequest(joinRequest, updateJoinRequest).subscribe();
  }

  decline(joinRequest: JoinRequest) {
    const updateJoinRequest: UpdateJoinTeamRequest = {
      joinStatus: JoinStatus.DECLINED
    };
    this.joinRequestService.handleJoinRequest(joinRequest, updateJoinRequest).subscribe();
  }

}
