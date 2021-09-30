package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL="/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value="Get all customers", notes="Very important call", produces = "application/json")
    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();

        return new ResponseEntity<>( new CustomerListDTO(allCustomers), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerByLastName(@PathVariable Long id) {
        return new ResponseEntity<>( customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO newCustomer = customerService.createNewCustomer(customerDTO);
        return new ResponseEntity<>( newCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO>createOrUpdateCustomer( @PathVariable Long id,
                                                              @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.createOrUpdate(id, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO>patchCustomer( @PathVariable Long id,
                                                     @RequestBody CustomerDTO customerDTO) {
        CustomerDTO patchCustomer = customerService.patchCustomer(id, customerDTO);
        return new ResponseEntity<>(patchCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
