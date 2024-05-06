create schema if not exists cccat16;

create table if not exists cccat16.account (
	account_id uuid primary key,
	name text not null,
	email text not null,
	cpf text not null,
	car_plate text null,
	is_passenger boolean not null default false,
	is_driver boolean not null default false
);

create table if not exists cccat16.ride (
    ride_id uuid primary key,
    passenger_id uuid,
    driver_id uuid,
    status text,
    fare numeric,
    distance numeric,
    from_lat numeric,
    from_long numeric,
    to_lat numeric,
    to_long numeric,
    date timestamp,
    last_lat numeric,
    last_long numeric
);

create table if not exists cccat16.position (
    position_id uuid primary key,
    ride_id uuid,
    latitude numeric,
    longitude numeric,
    date timestamp
);

create table if not exists cccat16.transaction (
    transaction_id uuid primary key,
    ride_id uuid,
    amount numeric,
    date timestamp,
    status text
);