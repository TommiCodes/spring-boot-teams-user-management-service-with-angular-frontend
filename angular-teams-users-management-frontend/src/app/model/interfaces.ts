import { User } from "./user.interfaces";

export interface AccessTokenPayload {
  sub: string;
  email: string;
  auth: string[];
  iat: number;
  exp: number
}

export type Privilege = 'MEMBER' | 'ADMIN';

export interface Auth {
  teamId: number;
  privileges: Privilege[];
}

export interface UserAuth extends User {
  sub: string;
  auth: Auth[];
  exp: number;
  iat: number;
}

export interface Pageable {
  size: number;
  number: number;
}

export interface Page {
  size: number,
  totalElements: number;
  totalPages: number;
  number: number;
}
