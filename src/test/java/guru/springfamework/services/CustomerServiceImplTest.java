package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerServiceImpl(customerMapper, customerRepository);
    }

    @Test
    @DisplayName("Get all customers")
    void getAllCustomers() {
        List<Customer> customers = createCustomerData();
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> allCustomers = service.getAllCustomers();

        assertEquals(2, allCustomers.size());

    }

    private List<Customer> createCustomerData() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Harry");
        customer1.setLastName("Smith");

        Customer customer2 = new Customer();
        customer2.setId(1L);
        customer2.setFirstName("DJ");
        customer2.setLastName("Bobo");

        return Arrays.asList(customer1, customer2);

    }

    @Test
    @DisplayName("Get a customer by its last name")
    void getCustomerByLastName() {
        List<Customer> customers = createCustomerData();
        when(customerRepository.findCustomerByLastName(anyString())).thenReturn(customers.get(0));

        CustomerDTO customerDTO = service.getCustomerByLastName("Smith");

        assertAll(
                () -> assertEquals(1L, customerDTO.getId()),
                () -> assertEquals("Harry", customerDTO.getFirstName()),
                () -> assertEquals("Smith", customerDTO.getLastName())
        );
    }

    @Test
    @DisplayName("Create a new customer")
    void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Hans");
        customerDTO.setLastName("Smith");

        Customer savedCustomer = customerMapper.customerDTOToCustomer(customerDTO);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO newCustomer = service.createNewCustomer(customerDTO);

        assertEquals(newCustomer.getFirstName(), customerDTO.getFirstName());
    }

    @Test
    @DisplayName("Update customer")
    void updateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Hans");
        customerDTO.setLastName("Smith");

        Customer savedCustomer = customerMapper.customerDTOToCustomer(customerDTO);
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDTO = service.createOrUpdate(1L, customerDTO);

        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
    }

    @Test
    @DisplayName("Deleting a customer")
    void deleteCustomer() {
        Long id = 1L;
        customerRepository.deleteById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}