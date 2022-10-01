package dao.impl;


import dao.CrudDAO;
import entitiy.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDaoImpl  {


    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    boolean add(Customer dto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean update(Customer dto) throws SQLException, ClassNotFoundException;

    ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException;
}
