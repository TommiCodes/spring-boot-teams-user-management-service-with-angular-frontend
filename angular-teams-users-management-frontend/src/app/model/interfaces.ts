
export interface AccessTokenPayload {
  sub: string;
  email: string;
  auth: string[];
  iat: number;
  exp: number
}

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

export interface Team {
  id?: number;
  name: string;
  users: User[];
  joinRequest: JoinRequest[];
}