 
    
    create table t_permission (
       permission_id bigint not null auto_increment,
        is_active bit not null,
        description varchar(255),
        permission_name varchar(255) not null,
        primary key (permission_id)
    ) engine=MyISAM;
 
    
    create table t_role (
       role_id bigint not null auto_increment,
        is_active bit not null,
        description varchar(255),
        role_name varchar(255) not null,
        primary key (role_id)
    ) engine=MyISAM;
 
    
    create table t_role_permission (
       role_id bigint not null,
        permission_id bigint not null
    ) engine=MyISAM;
 
    
    create table t_user (
       id bigint not null auto_increment,
        is_active bit not null,
        display_name varchar(255),
        email varchar(255),
        language varchar(255),
        location varchar(255),
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    ) engine=MyISAM;
 
    
    create table t_user_role (
       user_id bigint not null,
        role_id bigint not null
    ) engine=MyISAM;
 
    
    alter table t_permission 
       drop index UK_3dp1mlimbacwmxa5eflmtejny;
 
    
    alter table t_permission 
       add constraint UK_3dp1mlimbacwmxa5eflmtejny unique (permission_name);
 
    
    alter table t_role 
       drop index UK_bojr4duyesjymks0ty5yjwhx3;
 
    
    alter table t_role 
       add constraint UK_bojr4duyesjymks0ty5yjwhx3 unique (role_name);
 
    
    alter table t_user 
       drop index UK_jhib4legehrm4yscx9t3lirqi;
 
    
    alter table t_user 
       add constraint UK_jhib4legehrm4yscx9t3lirqi unique (username);
 
    
    alter table t_role_permission 
       add constraint FKjobmrl6dorhlfite4u34hciik 
       foreign key (permission_id) 
       references t_permission (permission_id);
 
    
    alter table t_role_permission 
       add constraint FK90j038mnbnthgkc17mqnoilu9 
       foreign key (role_id) 
       references t_role (role_id);
 
    
    alter table t_user_role 
       add constraint FKa9c8iiy6ut0gnx491fqx4pxam 
       foreign key (role_id) 
       references t_role (role_id);
 
    
    alter table t_user_role 
       add constraint FKq5un6x7ecoef5w1n39cop66kl 
       foreign key (user_id) 
       references t_user (id);