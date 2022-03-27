import { AllTeamsResolver } from './resolvers/all-teams/all-teams.resolver';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { OwnProfileResolver } from './resolvers/own-profile/own-profile.resolver';
import { TeamsPageComponent } from './pages/teams-page/teams-page.component';
import { ProtectedShellComponent } from './components/protected-shell/protected-shell.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { OwnTeamsResolver } from './resolvers/own-teams/own-teams.resolver';

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
