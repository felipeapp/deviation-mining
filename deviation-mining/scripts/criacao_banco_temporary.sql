CREATE DATABASE temporary
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'C'
       LC_CTYPE = 'C'
       CONNECTION LIMIT = -1;
       
       
       

CREATE SCHEMA temporary
  AUTHORIZATION postgres;

 
 ------------- Criação das tabelas para guardar os dados de forma temporária ----------------
  
CREATE TABLE temporary.scenarios_executions
(
  scenario text NOT NULL,
  "values" text,
  CONSTRAINT pk_scenarios_executions PRIMARY KEY (scenario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE temporary.scenarios_executions OWNER TO postgres;



-------- tabelas da analise de confiabilidade -------------

CREATE TABLE temporary.scenarios_error
(
  scenario text NOT NULL,
  CONSTRAINT pk_scenario_error PRIMARY KEY (scenario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE temporary.scenarios_error
  OWNER TO postgres;

  
  
CREATE TABLE temporary.scenarios_error_trace
(
  scenario text,
  trace text,
  CONSTRAINT fk_scenarios_error FOREIGN KEY (scenario)
      REFERENCES temporary.scenarios_error (scenario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE temporary.scenarios_error_values
  OWNER TO postgres;
  
  

