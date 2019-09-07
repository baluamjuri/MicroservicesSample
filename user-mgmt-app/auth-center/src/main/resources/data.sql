insert into t_user(username, password, is_active) values('john', '$2a$10$J.89bFmBY402UOb.HSuD4eITPi2vv7fUoSXuyE.lVeN2VHMp0io9e',true);

insert into t_role(role_name, is_active) values('ADMIN',true);
insert into t_role(role_name, is_active) values('MANAGER',true);
insert into t_role(role_name, is_active) values('TEAM_LEAD',true);
insert into t_role(role_name, is_active) values('TEAM_MEMBER',true);

insert into t_permission(permission_name, is_active) values('USER_CREATE',true);
insert into t_permission(permission_name, is_active) values('USER_READ',true);
insert into t_permission(permission_name, is_active) values('USER_UPDATE',true);
insert into t_permission(permission_name, is_active) values('USER_DELETE',true);
insert into t_permission(permission_name, is_active) values('PASSWORD_UPDATE',true);
insert into t_permission(permission_name, is_active) values('PERMISSION_CREATE',true);
insert into t_permission(permission_name, is_active) values('PERMISSION_READ',true);
insert into t_permission(permission_name, is_active) values('PERMISSION_UPDATE',true);
insert into t_permission(permission_name, is_active) values('PERMISSION_DELETE',true);
insert into t_permission(permission_name, is_active) values('ROLES_CREATE',true);
insert into t_permission(permission_name, is_active) values('ROLES_READ',true);
insert into t_permission(permission_name, is_active) values('ROLES_UPDATE',true);
insert into t_permission(permission_name, is_active) values('ROLES_DELETE',true);

insert into t_user_role(user_id, role_id) values(1,1);

insert into t_role_permission(role_id, permission_id) values(1,1);
insert into t_role_permission(role_id, permission_id) values(1,2);
insert into t_role_permission(role_id, permission_id) values(1,3);
insert into t_role_permission(role_id, permission_id) values(1,4);
insert into t_role_permission(role_id, permission_id) values(1,5);
insert into t_role_permission(role_id, permission_id) values(1,6);
insert into t_role_permission(role_id, permission_id) values(1,7);
insert into t_role_permission(role_id, permission_id) values(1,8);
insert into t_role_permission(role_id, permission_id) values(1,9);
insert into t_role_permission(role_id, permission_id) values(1,10);
insert into t_role_permission(role_id, permission_id) values(1,11);
insert into t_role_permission(role_id, permission_id) values(1,12);
insert into t_role_permission(role_id, permission_id) values(1,13);