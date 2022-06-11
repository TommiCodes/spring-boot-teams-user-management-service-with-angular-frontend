import { User } from 'src/app/models/user.interfaces';
import { Page, Role } from "./interfaces"


export interface UserTeamRelation {
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

export interface UserTeamRelationPagedResponse {
  _embedded: {
    userTeamRelations: UserTeamRelation[];
  };
  _links: {
    self: {
      href: string;
    }
  };
  page: Page;
}
