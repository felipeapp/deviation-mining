package br.ufrn.ase.dao;

import java.util.List;

import br.ufrn.ase.domain.RegistroEntrada;

/**
 * Database operations for RegistroEntrada.
 * 
 * @author Felipe
 */
public interface RegistroEntradaDAO {

	public List<RegistroEntrada> findAllBySystemVersion(String system_version, String... fields);

}
