create table project_steps(
    id int primary key auto_increment,
    description varchar(100) null,
    days_to_deadline int,
    project_id int
);

alter table project_steps add foreign key (project_id) references projects (id);