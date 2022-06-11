import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { UserTeamRelation } from 'src/app/models/user-team-relation.interfaces';
import { User } from 'src/app/models/user.interfaces';
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Role } from 'src/app/models/interfaces';

export interface DialogData {
  userTeam: UserTeamRelation;
}

@Component({
  selector: 'app-team-member-edit-dialog',
  templateUrl: './team-member-edit-dialog.component.html',
  styleUrls: ['./team-member-edit-dialog.component.scss']
})
export class TeamMemberEditDialogComponent {

  roleForm: UntypedFormGroup = new UntypedFormGroup({
    role: new UntypedFormControl(this.data.userTeam.role.role)
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
