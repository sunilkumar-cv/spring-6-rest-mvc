package sunil.springframework.spring6restmvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sunil.springframework.spring6restmvc.entities.Beer;
import sunil.springframework.spring6restmvc.entities.Customer;
import sunil.springframework.spring6restmvc.mappers.CustomerMapper;
import sunil.springframework.spring6restmvc.model.BeerDTO;
import sunil.springframework.spring6restmvc.model.CustomerDTO;
import sunil.springframework.spring6restmvc.repositories.BeerRepository;
import sunil.springframework.spring6restmvc.repositories.CustomerRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIntegTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    private BeerRepository beerRepository;

    @Test
    void testGetAllCustomers() {
        List<CustomerDTO> dtos = customerController.getAllCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyCustomerList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.getAllCustomers();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
           customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                    .name("Sandeep Reddy Vanga")
                .build();
        ResponseEntity responseEntity = customerController.createCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingCustomerTest() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "New Customer Name";
        customerDTO.setName(customerName);
        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    void updateCustomerNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
           customerController.updateCustomer(UUID.randomUUID(),
                   CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomerTest() {
        Customer customer = customerRepository.findAll().getFirst();
        ResponseEntity responseEntity = customerController.deleteCustomer(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    void deleteCustomerNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomer(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void patchExistingBeerTest() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String beerName = "UPDATED Beer";
        customerDTO.setName(beerName);

        ResponseEntity responseEntity = customerController.patchCustomer(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(beerName);
    }

    @Test
    void testPatchBeerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.patchCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }
}