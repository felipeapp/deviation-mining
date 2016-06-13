

CREATE TABLE result.median_time_execution
(
  system_version character varying(20),
  scenario text NOT NULL,
  median double precision,
  CONSTRAINT pk_median_time_execution PRIMARY KEY (scenario, system_version)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.median_time_execution OWNER TO postgres;