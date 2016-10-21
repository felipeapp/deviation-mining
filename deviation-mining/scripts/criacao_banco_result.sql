CREATE DATABASE result
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'C'
       LC_CTYPE = 'C'
       CONNECTION LIMIT = -1;
       
       
       

CREATE SCHEMA result
  AUTHORIZATION postgres;
  

------------- Criação das tabelas para guardar o resultado do processamento ----------------  
  
  
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



---------- analise de confiablidade -----------------------


CREATE TABLE result.total_of_error_by_scenario
(
  system_version text NOT NULL,
  scenario text NOT NULL,
  qtd_error integer,
  CONSTRAINT pk_total_of_error_by_scenario PRIMARY KEY (system_version, scenario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.total_of_error_by_scenario OWNER TO postgres;




CREATE TABLE result.total_of_error_by_trace
(
  system_version text NOT NULL,
  scenario text NOT NULL,
  trace text NOT NULL,
  qtd_error integer,
  CONSTRAINT pk_total_of_error_by_trace PRIMARY KEY (system_version, scenario, trace)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.total_of_error_by_trace OWNER TO postgres;





------------------------------------------------
--- guarda a porcentagem de erros do sistema ---
------------------------------------------------

CREATE TABLE result.error_statistic
(
  system_version character varying(100) NOT NULL,
  scenario text NOT NULL,
  qtd_access integer,
  qtd_error integer,
  percentage numeric(8,2),
  CONSTRAINT pk_error_statistic PRIMARY KEY (system_version, scenario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.error_statistic OWNER TO postgres;



-------------------------------------------------------------------------
 -- Guarda o resultado dos erros por linha de código em que ocorreram 
--------------------------------------------------------------------------


CREATE TABLE result.infra_error_scenario
(
  scenario text NOT NULL,
  qtd integer, -- quantidade total, soma das quantidades de todos os erros nas linhas de código
  CONSTRAINT pk_infra_erro_scenario PRIMARY KEY (scenario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.infra_error_scenario OWNER TO postgres;
COMMENT ON COLUMN result.infra_error_scenario.qtd IS 'quantidade total, soma das quantidades de todos os erros nas linhas de código';



  
  
 CREATE TABLE result.infra_error_scenario_line_of_code
(
  scenario text NOT NULL,
  line_of_code text NOT NULL,
  qtd integer,
  CONSTRAINT pk_infra_error_scenario_line_of_code PRIMARY KEY (scenario, line_of_code),
  CONSTRAINT fk_infra_error_scenario_line_of_code FOREIGN KEY (scenario)
      REFERENCES result.infra_error_scenario (scenario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE result.infra_error_scenario_line_of_code OWNER TO postgres;
 
  



