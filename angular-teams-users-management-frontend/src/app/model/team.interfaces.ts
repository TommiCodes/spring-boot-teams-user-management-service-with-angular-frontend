import { Page } from "./interfaces";
import { JoinRequest } from "./join-request.interfaces";
import { User } from "./user.interfaces";

export interface Team {
  id?: number;
  name: string;
  users: User[];
  joinRequest: JoinRequest[];
  _links?: {
    self: {
      href: string;
    };
    team: {
      href: string;
    };
    users: {
      href: string;
    };
    joinRequests: {
      href: string;
      templated: boolean;
    }
  }
}

export interface TeamsPagedResponse {
  _embedded: {
    teams: Team[];
  };
  _links: {
    self: {
      href: string;
    }
  };
  page: Page;
}
