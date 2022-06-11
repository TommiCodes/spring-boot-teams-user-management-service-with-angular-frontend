# spring-boot-teams-user-management-service-with-angular-frontend

**Please Remember that this is a hobby project and done in the freetime**

This project aims to show how we could handle the membership of a user in multiple teams, where the user has a ROLE for every Team where he is a member.  
**A User can be a MEMBER or ADMIN in multiple Teams. The Roles are specific for the USER/TEAM relation.**  
e.g. User Arnold is in Team 1 and Team 2 and Team 3. In Team 1 he is an ADMIN, in Team 2 he is also ADMIN and in Team 3 he is MEMBER.

It is also shown how to create Teams, accept join Requests from other users and handle the roles for each user per team.

**Backend (Some basic Information):**
- All Requests (except to register/login) are JWT protected
- All specific Endpoints that need special Protection are also checking the ROLE of the user for the specific Request  
  ... e.g. If a User tries to remove an other User from team (teamId=1) then it is checked if the User who is trying to   
  remove the other user from the team has ADMIN rights for this team ...

**Frontend (Some basic Information):**
- Only /register and /login routes are public, other routes are protected by a guard
- For every Request (besides to /api/login|register) the JWT is added to authenticate against the backend

**A User can do specific stuff (for Teams):**
- can be a MEMBER or ADMIN in multiple teams
- create an own team where he is ADMIN
- accept join requests for a team where he has the ADMIN role
- change the role of a user in a team where he is an ADMIN
- can remove a user from a team where he is an ADMIN
- join multiple other teams (send a join Request to an other or multiple other Teams)

**A User can do some basic stuff:**
- register
- login
- logout
- update own profile
- Look at own dashboard
- paginate through all users page & search for user by username
- paginate trough all teams page & search for team by teamname

## Run the Project
**Run the Spring Boot Backend:**  
`cd spring-boot-teams-user-management-service`  
`mvn clean install`
`mvn spring-boot:run`  
  
**Run the Angular Frontend:**  
`cd angular-teams-users-management-frontend`  
`npm install`  
`ng serve`
