import { Page } from "./interfaces";
import { Team } from "./team.interfaces";
import { User } from "./user.interfaces";

export interface NewJoinRequest {
  userId: number;
}

export interface UpdateJoinTeamRequest {
  joinStatus: JoinStatus;
}

export interface HandleJoinRequest {
  updateJoinTeamRequest: UpdateJoinTeamRequest;
  joinRequest: JoinRequest;
}

export enum JoinStatus {
  'INQUIRY',
  'ACCEPTED',
  'DECLINED'
}
export interface JoinRequest {
  id?: number;
  joinStatus: JoinStatus;
  user: User;
  team: Team;
}

export interface JoinRequestPageResponse {
  _embedded: {
    joinRequests: JoinRequest[];
  };
  _links: {
    self: {
      href: string;
    }
  };
  page: Page;
}


