import { Page } from "./interfaces";
import { JoinRequest } from "./join-request.interfaces";
import { Team } from "./team.interfaces";

export interface User {
  id?: number;
  username: string;
  email: string;
  firstname: string;
  lastname: string;
  password?: string;
  joinRequest: JoinRequest[];
  teams: Team[];
}

export interface UserPagedResponse {
  _embedded: {
    users: User[];
  };
  _links: {
    self: {
      href: string;
    }
  };
  page: Page;
}
