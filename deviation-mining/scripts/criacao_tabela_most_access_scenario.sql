

CREATE TABLE result.most_access_scenarios
(
  system_version character varying(20),
  scenario text NOT NULL,
  qtd_access integer,
  CONSTRAINT pk_most_access_scenarios PRIMARY KEY (scenario, system_version)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.most_access_scenarios OWNER TO postgres;