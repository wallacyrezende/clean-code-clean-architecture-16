create schema if not exists cccat16;

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