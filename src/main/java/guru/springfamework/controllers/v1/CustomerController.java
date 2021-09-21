package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL="/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();

        return new ResponseEntity<>( new CustomerListDTO(allCustomers), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerByLastName(@PathVariable Long id) {
        return new ResponseEntity<>( customerService.getCustomerById(id), HttpStatus.OK);
    }

}
