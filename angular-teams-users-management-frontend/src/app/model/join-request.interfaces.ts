import { Team } from "./team.interfaces";
import { User } from "./user.interfaces";

export interface JoinRequest {
  id?: number;
  joinStatus: JoinStatus;
  user: User;
  team: Team;
}

export enum JoinStatus {
  'INQUIRY',
  'ACCEPTED',
  'DECLINED'
}