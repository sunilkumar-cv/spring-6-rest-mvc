package sunil.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunil.springframework.spring6restmvc.model.CustomerDTO;
import sunil.springframework.spring6restmvc.services.CustomerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //@RequestMapping(value ="/{customerId}", method = RequestMethod.GET)
    @RequestMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID id) {
        return customerService.getCustomerById(id).orElseThrow(NotFoundException :: new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
        if(customerService.updateCustomerById(customerId, customer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomer(@PathVariable("customerId") UUID customerId) {
        if(!customerService.deleteCustomerById(customerId)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
        if(customerService.patchCustomerById(customerId, customer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
