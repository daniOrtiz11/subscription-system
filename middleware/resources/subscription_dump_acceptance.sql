CREATE SCHEMA "subscription-sch";

CREATE TABLE "subscription-sch"."SUBSCRIPTIONS_TEST" (
    email varchar(1000) NOT NULL,
    name varchar(1000),
    gender varchar(1000),
    birth_date varchar(1000) NOT NULL,
    consent boolean NOT NULL,
    newsletter_id varchar NOT NULL,
    PRIMARY KEY (email, newsletter_id)
);
