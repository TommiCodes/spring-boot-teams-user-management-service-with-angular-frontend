import { MatSnackBar } from '@angular/material/snack-bar';
import { TeamMemberEditDialogComponent } from './../team-member-edit-dialog/team-member-edit-dialog.component';
import { Role } from './../../../model/interfaces';
import { Router } from '@angular/router';
import { TeamService } from './../../services/team-service/team.service';
import { outputAst } from '@angular/compiler';
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Pageable } from 'src/app/model/interfaces';
import { Team } from 'src/app/model/team.interfaces';
import { UserTeam, UserTeamPagedResponse } from 'src/app/model/user-team.interfaces';
import { switchMap, tap } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { snackBarConf } from 'src/app/model/consts';

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
  membersDisplayedCols: string[] = ['id', 'email', 'firstname', 'lastname', 'role', 'actions'];

  roleList: Role[] = ['MEMBER', 'ADMIN'];

  constructor(private teamService: TeamService, private router: Router, private dialog: MatDialog, private snack: MatSnackBar) { }

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

  removeUser(userTeam: UserTeam) {
    this.teamService.removeUserFromTeam(this.team!, userTeam.user).pipe(
      tap(() => this.router.navigate([]))
    ).subscribe();
  }

  openDialog(userTeam: UserTeam) {
    const dialogRef = this.dialog.open(TeamMemberEditDialogComponent, {
      width: '250px',
      data: { userTeam: userTeam },
    });

    dialogRef.afterClosed().pipe(
      switchMap((newRole: Role) => this.teamService.updateRoleOfUser(newRole, this.team!, userTeam.user).pipe(
        tap(() => this.snack.open('User Role was updatet', 'Close', snackBarConf))
      ))
    ).subscribe(result => {
      console.log('The dialog was closed');
    });

  }

}
