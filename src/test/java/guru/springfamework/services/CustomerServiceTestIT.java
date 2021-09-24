package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CustomerServiceTestIT {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    @DisplayName("Updating first name of a customer")
    void patchCustomerUpdateFirstName() {
        Long firstId = getFirstCustomerIdFromBootstrap();

        Customer originalCustomer = customerRepository.getById(firstId);
        assertNotNull(originalCustomer);

        String originalCustomerLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("UpdatedName");

        customerService.patchCustomer(firstId, customerDTO);

        CustomerDTO patchedCustomer = customerService.getCustomerById(firstId);

        assertNotNull(patchedCustomer);
        assertThat(originalCustomerLastName, equalTo(patchedCustomer.getLastName()));
        assertThat("UpdatedName", equalTo(patchedCustomer.getFirstName()));
    }

    private Long getFirstCustomerIdFromBootstrap() {
        List<Customer> allCustomers = customerRepository.findAll();
        Long firstId = allCustomers.get(0).getId();
        return firstId;
    }

}
