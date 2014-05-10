package bdd.model;

import java.util.List;

public interface GenericDAO<T, ID> {

    public List<T> selectAll() throws DAOException;

    public T find(ID id) throws DAOException;

    public boolean insert(T t) throws DAOException;

    public boolean update(T t) throws DAOException;

    public boolean delete(T t) throws DAOException;
}