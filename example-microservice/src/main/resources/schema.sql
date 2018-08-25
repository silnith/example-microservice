create table domains (
    domain_id national character varying(64) primary key,
    description national character varying(512)
)

create table regions (
    region_id national character varying(64) primary key,
    description national character varying(512)
)

create table transactions (
    transaction_id national character varying(256) primary key,
    domain national character varying(64) not null foreign key references domains (domain_id),
    region national character varying(64) not null foreign key references regions (region_id),
    count integer not null,
    created timestamp with time zone not null default current_timestamp
)
