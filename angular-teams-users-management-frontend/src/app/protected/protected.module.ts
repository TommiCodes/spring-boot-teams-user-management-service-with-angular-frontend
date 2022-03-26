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

@NgModule({
  declarations: [
    ProtectedShellComponent,
    DashboardPageComponent,
    TeamsComponent,
    TeamsPageComponent,
    UserProfileComponent,
    UpdateProfileComponent,
  ],
  imports: [
    CommonModule,
    // Import our Routes for this module
    ProtectedRoutingModule,
    // Angular Material Imports
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatDividerModule,
    MatCardModule
  ],
})
export class ProtectedModule {}
