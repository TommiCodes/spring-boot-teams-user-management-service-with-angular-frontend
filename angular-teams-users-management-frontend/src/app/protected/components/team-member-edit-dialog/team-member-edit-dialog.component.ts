import { FormControl, FormGroup } from '@angular/forms';
import { UserTeam } from 'src/app/model/user-team.interfaces';
import { User } from 'src/app/model/user.interfaces';
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Role } from 'src/app/model/interfaces';

export interface DialogData {
  userTeam: UserTeam;
}

@Component({
  selector: 'app-team-member-edit-dialog',
  templateUrl: './team-member-edit-dialog.component.html',
  styleUrls: ['./team-member-edit-dialog.component.scss']
})
export class TeamMemberEditDialogComponent {

  roleForm: FormGroup = new FormGroup({
    role: new FormControl(this.data.userTeam.role.role)
  })

  role: Role | null = null;
  roles: Role[] = ['MEMBER', 'ADMIN'];

  constructor(
    public dialogRef: MatDialogRef<TeamMemberEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updateRole(role: Role) {
    this.role = role;
  }

}
