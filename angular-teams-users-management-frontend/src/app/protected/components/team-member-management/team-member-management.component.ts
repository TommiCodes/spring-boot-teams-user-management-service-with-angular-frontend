import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Pageable } from 'src/app/model/interfaces';
import { Team } from 'src/app/model/team.interfaces';
import { UserTeam, UserTeamPagedResponse } from 'src/app/model/user-team.interfaces';

@Component({
  selector: 'app-team-member-management',
  templateUrl: './team-member-management.component.html',
  styleUrls: ['./team-member-management.component.scss']
})
export class TeamMemberManagementComponent {

  @Input() team!: Team | null;
  @Input() teamMembers!: UserTeamPagedResponse | null;

  @Output() paginateMembers: EventEmitter<Pageable> = new EventEmitter<Pageable>();

  membersDataSource: MatTableDataSource<UserTeam> = new MatTableDataSource<UserTeam>();
  membersDisplayedCols: string[] = ['id', 'email', 'firstname', 'lastname', 'role'];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['teamMembers'].currentValue) {
      this.membersDataSource.data = this.teamMembers!._embedded.userTeams;
    }      
  }

  handlePageEvent(pageEvent: PageEvent) {
    this.paginateMembers.emit({
      size: pageEvent.pageSize,
      number: pageEvent.pageIndex
    });
  }

}
