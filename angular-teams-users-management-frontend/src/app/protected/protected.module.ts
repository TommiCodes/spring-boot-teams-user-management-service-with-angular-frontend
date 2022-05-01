import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { MatIconModule } from '@angular/material/icon';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProtectedRoutingModule } from './protected-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDividerModule } from '@angular/material/divider';
import { ProtectedShellComponent } from './components/protected-shell/protected-shell.component';
import { TeamsComponent } from './components/teams/teams.component';
import { TeamsPageComponent } from './pages/teams-page/teams-page.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { TeamProfilePageComponent } from './pages/team-profile-page/team-profile-page.component';
import { TeamProfileComponent } from './components/team-profile/team-profile.component';
import { TeamAdminPageComponent } from './pages/team-admin-page/team-admin-page.component';
import { TeamJoinRequestsComponent } from './components/team-join-requests/team-join-requests.component';
import {MatTabsModule} from '@angular/material/tabs';
import { TeamMemberManagementComponent } from './components/team-member-management/team-member-management.component';
import { MatSelectModule } from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';
import { TeamMemberEditDialogComponent } from './components/team-member-edit-dialog/team-member-edit-dialog.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { UsersComponent } from './components/users/users.component';

@NgModule({
  declarations: [
    ProtectedShellComponent,
    DashboardPageComponent,
    TeamsComponent,
    TeamsPageComponent,
    UserProfileComponent,
    UpdateProfileComponent,
    TeamProfilePageComponent,
    TeamProfileComponent,
    TeamAdminPageComponent,
    TeamJoinRequestsComponent,
    TeamMemberManagementComponent,
    TeamMemberEditDialogComponent,
    UsersPageComponent,
    UsersComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    // Import our Routes for this module
    ProtectedRoutingModule,
    // Angular Material Imports
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatDividerModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatTabsModule,
    MatSelectModule,
    MatDialogModule
  ],
})
export class ProtectedModule { }
