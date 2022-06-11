import { User } from "./user.interfaces";

export interface AccessTokenPayload {
  sub: string;
  email: string;
  auth: string[];
  iat: number;
  exp: number
}

// The ADMIN Role has the privileges ADMIN & MEMBER
// THE MEMBER Role has the privilege MEMBER

export type Role = 'ADMIN' | 'MEMBER';
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
