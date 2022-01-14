-- CREATE SEQUENCES
CREATE SEQUENCE user_seq;
CREATE SEQUENCE transaction_seq;



-- CREATE TABLES
CREATE TABLE public."user"
(
    id INT DEFAULT NEXTVAL('user_seq') PRIMARY KEY   NOT NULL,
    user_name character varying NOT NULL,
    user_mail character varying NOT NULL,
    user_password character varying  NOT NULL,
    virtual_currency numeric DEFAULT 0
);


CREATE TABLE public.users_transactions
(
    id INT DEFAULT NEXTVAL('transaction_seq') PRIMARY KEY NOT NULL,
    from_user integer NOT NULL,
    to_user integer NOT NULL,
    transaction_date timestamp without time zone NOT NULL default CURRENT_TIMESTAMP,
    CONSTRAINT "fromUser_con" FOREIGN KEY (from_user)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "toUser_con" FOREIGN KEY (to_user)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

