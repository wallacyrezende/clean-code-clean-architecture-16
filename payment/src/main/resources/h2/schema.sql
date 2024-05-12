create schema if not exists cccat16;

create table if not exists cccat16.transaction (
    transaction_id uuid primary key,
    ride_id uuid,
    amount numeric,
    date timestamp,
    status text
);