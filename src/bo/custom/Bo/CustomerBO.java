package bo.custom.Bo;

import bo.custom.SuperBO;
import dto.CustomerDto;


import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {


    boolean addCustomer(CustomerDto customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto customerDTO) throws SQLException, ClassNotFoundException;


    ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

}
