package bo.custom.impl;

import bo.custom.Bo.CustomerBO;
import dao.impl.CustomerDao;
import dto.CustomerDto;
import entitiy.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBO {

    private final CustomerDao customerDAO = new CustomerDao();

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {

        return customerDAO.ifCustomerExist(id);

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.deleteCustomer(id);
    }





    @Override
    public boolean addCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {

            return customerDAO.add(new Customer(dto.getId(), dto.getName(), dto.getAddress()));

    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
    }




    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDto(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return allCustomers;
    }

}
