package sunil.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sunil.springframework.spring6restmvc.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.debug("Listing all customers data");
        return new ArrayList<CustomerDTO>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("Get Customer By Id - in service");
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(customer.getVersion())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        existingCustomer.setName(customer.getName());
        existingCustomer.setVersion(customer.getVersion());
        existingCustomer.setUpdatedDate(LocalDateTime.now());
        customerMap.put(customerId, existingCustomer);
        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        if(StringUtils.hasText(customer.getName())) {
            existingCustomer.setName(customer.getName());
        }

        if(customer.getVersion() != null) {
            existingCustomer.setVersion(customer.getVersion());
        }
        return Optional.of(existingCustomer);
    }
}
