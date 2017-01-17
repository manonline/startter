CREATE TABLE public."user"
(
  id bigint NOT NULL, -- primary key of user table
  name text NOT NULL,
  age integer NOT NULL,
  gender integer NOT NULL,
  email text,
  phone text,
  CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."user"
  OWNER TO postgres;