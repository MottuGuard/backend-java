-- Roles/Users
create table roles (
  id bigserial primary key,
  name varchar(50) not null unique
);
create table users (
  id bigserial primary key,
  name varchar(120) not null,
  email varchar(180) not null unique,
  password varchar(200) not null
);
create table user_roles (
  user_id bigint not null references users(id) on delete cascade,
  role_id bigint not null references roles(id) on delete cascade,
  primary key (user_id, role_id)
);

-- Domain
create table motos (
  id bigserial primary key,
  chassi varchar(64),
  placa varchar(16),
  modelo varchar(32) not null,
  status varchar(32) not null,
  lastx double precision,
  lasty double precision,
  lastseenat timestamp with time zone,
  createdat timestamp with time zone not null,
  updatedat timestamp with time zone not null
);
create table uwb_tags (
  id bigserial primary key,
  eui64 varchar(64) not null unique,
  status varchar(32) not null,
  moto_id bigint unique references motos(id) on delete set null
);
create table uwb_anchors (
  id bigserial primary key,
  name varchar(80) not null unique,
  x double precision not null,
  y double precision not null,
  z double precision not null
);
create table uwb_measurements (
  id bigserial primary key,
  tag_id bigint not null references uwb_tags(id) on delete cascade,
  anchor_id bigint not null references uwb_anchors(id) on delete cascade,
  timestamp timestamp with time zone not null,
  distance double precision not null,
  rssi double precision not null
);
create table position_records (
  id bigserial primary key,
  moto_id bigint not null references motos(id) on delete cascade,
  timestamp timestamp with time zone not null,
  x double precision not null,
  y double precision not null
);