CREATE TABLE public."user"
(
  id bigint NOT NULL,
  name text NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."user"
  OWNER TO starter;