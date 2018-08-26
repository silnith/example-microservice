create table domains (
    domain_id character varying(64) primary key,
    description character varying(512)
);

create table regions (
    region_id character varying(64) primary key,
    description character varying(512)
);

create table transactions (
    transaction_id character varying(256) primary key,
    domain character varying(64) not null foreign key references domains (domain_id),
    region character varying(64) not null foreign key references regions (region_id),
    count integer not null,
    created timestamp with time zone not null default current_timestamp
);
