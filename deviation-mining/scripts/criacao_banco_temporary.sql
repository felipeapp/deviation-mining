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
  
  
  
  
  
  
 -----  tabela que guarda as informações da tabela infra.error -------------
 
  
  
CREATE TABLE temporary.infra_error_scenario_line_of_code
(
  scenario_line_of_code text NOT NULL,
  qtd integer,
  CONSTRAINT pk_infra_error_scenario_line_erro PRIMARY KEY (scenario_line_of_code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE temporary.infra_error_scenario_line_of_code
  OWNER TO postgres;
COMMENT ON TABLE temporary.infra_error_scenario_line_of_code
  IS 'Tabela que guarda de forma temporária o cenário a a linha de erro onde o erro ocorreu.';
  
  
  

