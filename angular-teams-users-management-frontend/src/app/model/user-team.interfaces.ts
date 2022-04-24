import { User } from 'src/app/model/user.interfaces';
import { Page, Role } from "./interfaces"


export interface UserTeam {
  role: {
    role: Role
  };
  user: User;
  _links?: {
    self: {
      href: string;
    };
  }
}

export interface UserTeamPagedResponse {
  _embedded: {
    userTeams: UserTeam[];
  };
  _links: {
    self: {
      href: string;
    }
  };
  page: Page;
}
