

CREATE TABLE result.variation_time_range
(
  system_version character varying(20),
  scenario text NOT NULL,
  variation double precision,
  CONSTRAINT pk_variation_time_range PRIMARY KEY (scenario, system_version)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.variation_time_range OWNER TO postgres;