package sunil.springframework.spring6restmvc.mappers;

import org.mapstruct.Mapper;
import sunil.springframework.spring6restmvc.entities.Customer;
import sunil.springframework.spring6restmvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
