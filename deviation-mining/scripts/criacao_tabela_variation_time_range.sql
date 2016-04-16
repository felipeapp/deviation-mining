

CREATE TABLE result.variation_time_range
(
  scenario text NOT NULL,
  system_version character varying(20),
  variation double precision,
  CONSTRAINT pk PRIMARY KEY (scenario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.variation_time_range
  OWNER TO postgres;