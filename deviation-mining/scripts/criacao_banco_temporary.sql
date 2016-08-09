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