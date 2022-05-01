import { UsersResolver } from './resolvers/users-resolver/users.resolver';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { UserIsTeamAdminGuard } from './../guards/user-is-team-admin-guard/user-is-team-admin.guard';
import { TeamAdminPageComponent } from './pages/team-admin-page/team-admin-page.component';
import { TeamJoinRequestsResolver } from './resolvers/team-join-requests-resolver/team-join-requests.resolver';
import { TeamMembersResolver } from './resolvers/team-members-resolver/team-members.resolver';
import { TeamResolver } from './resolvers/team-resolver/team.resolver';
import { TeamProfilePageComponent } from './pages/team-profile-page/team-profile-page.component';
import { AllTeamsResolver } from './resolvers/all-teams-resolver/all-teams.resolver';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { OwnProfileResolver } from './resolvers/own-profile-resolver/own-profile.resolver';
import { TeamsPageComponent } from './pages/teams-page/teams-page.component';
import { ProtectedShellComponent } from './components/protected-shell/protected-shell.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { OwnTeamsResolver } from './resolvers/own-teams-resolver/own-teams.resolver';

// Routes for child Module (protectedModule). Since protected module is lazy loaded in in the
// app-routing.module the full path is `/protected/dashboard`
const routes: Routes = [
  {
    path: '',
    component: ProtectedShellComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardPageComponent,
        resolve: {
          ownProfile: OwnProfileResolver,
          ownTeams: OwnTeamsResolver
        },
      },
      {
        path: 'my-profile',
        component: UpdateProfileComponent,
        resolve: {
          ownProfile: OwnProfileResolver
        }
      },
      {
        path: 'teams',
        component: TeamsPageComponent,
        // If the query Params change, then the resolver should run again (here used for pagination)
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
        resolve: {
          allTeams: AllTeamsResolver
        }
      },
      {
        path: 'teams/:id',
        component: TeamProfilePageComponent,
        // If the path Params change, then the resolver should run again (here used to load the team by his id)
        runGuardsAndResolvers: 'pathParamsChange',
        resolve: {
          team: TeamResolver,
          teamMembers: TeamMembersResolver
        }
      },
      {
        path: 'teams/:id/admin',
        component: TeamAdminPageComponent,
        runGuardsAndResolvers: 'pathParamsChange',
        canActivate: [UserIsTeamAdminGuard],
        resolve: {
          team: TeamResolver,
          joinRequests: TeamJoinRequestsResolver,
          teamMembers: TeamMembersResolver
        }
      },
      {
        path: 'users',
        component: UsersPageComponent,
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
        resolve: {
          users: UsersResolver
        }
      },
      {
        path: '**',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'login',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProtectedRoutingModule { }
