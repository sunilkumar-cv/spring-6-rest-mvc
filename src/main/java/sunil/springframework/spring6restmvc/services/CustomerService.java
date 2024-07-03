package sunil.springframework.spring6restmvc.services;

import sunil.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);
}
