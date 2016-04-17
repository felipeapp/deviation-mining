package br.ufrn.ase.dao;

import java.util.Date;
import java.util.List;

import br.ufrn.ase.domain.RegistroEntrada;

/**
 * Interface for Database operations for RegistroEntrada.
 * 
 * @author Felipe
 */
public interface RegistroEntradaDAO {

	public RegistroEntrada findByID(int idEntrada);

	public List<Integer> getIDListGreaterThan(int idEntrada);

	public int getMaxIdEntrada();

	public void insert(RegistroEntrada entrada);

	public List<RegistroEntrada> findAllBySystemVersion(String systemName, Date initialDate, Date finalDate);

}
