DROP TABLE IF EXISTS authorities ;
DROP TABLE IF EXISTS users ;

create table users (   
  username varchar(50) not null primary key,
  password varchar(255) not null,
  enabled boolean not null) ;
  


create table authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  foreign key (username) references users (username),
  unique index authorities_idx_1 (username, authority));

