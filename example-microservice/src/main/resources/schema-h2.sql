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
    domain character varying(64) not null,
    region character varying(64) not null,
    count integer not null,
    created timestamp with time zone not null default current_timestamp,
    foreign key(domain) references domains (domain_id),
    foreign key(region) references regions (region_id),
);
