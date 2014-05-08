package bdd.model;

import java.util.List;

/**
 * Generic DAO interface in order to provide the basic CRUD operations.
 * 
 * 
 * @param <T>
 *            The type of elements maintained by this set.
 * 
 * @param <ID>
 *            The type of the ID's element maintained by this set.
 */

public interface GenericDAO<T, ID> {

	/**
	 * List of elements, identified by the generic type, from the database
	 * ordered by user ID. The list is never null and is empty when the database
	 * does not contain any user.
	 * 
	 * @return a list of elements, identified by the generic type, from the
	 *         database ordered by user ID.
	 * @throws DAOException
	 */
	public List<T> selectAll() throws DAOException;

	/**
	 * Find the element inside the database by the primary key.
	 * 
	 * @param id
	 *            The ID of the element that has to be search.
	 * @return the element from the database matching the given ID.
	 * @throws DAOException
	 *             If something fails at database level.
	 */

	public T find(ID id) throws DAOException;

	/**
	 * Create the given element in the database. The ID must be 0, otherwise it
	 * will throw IllegalArgumentException.
	 * 
	 * @param t
	 *            The element that has to be created in the database.
	 * @return the ID if the element has been created successfully, otherwise
	 *         -1.
	 * @throws IllegalArgumentException
	 *             If the ID is not 0.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void insert(T t) throws IllegalArgumentException, DAOException;

	/**
	 * Delete the given element from the database.
	 * 
	 * @param t
	 *            The element that has to be deleted from the database.
	 * @return true if the element has been deleted successfully, otherwise
	 *         false.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void delete(T t) throws DAOException;

}