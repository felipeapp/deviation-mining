

CREATE TABLE result.average_time_execution
(
  system_version character varying(20),
  scenario text NOT NULL,
  average double precision,
  CONSTRAINT pk_average_time_execution PRIMARY KEY (scenario, system_version)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.average_time_execution OWNER TO postgres;