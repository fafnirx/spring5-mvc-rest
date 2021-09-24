package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get all customers")
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customers = Arrays.asList(new CustomerDTO(),
                new CustomerDTO(),
                new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    @DisplayName("Get a customer by its id")
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(2L);
        customerDTO.setFirstName("Harry");
        customerDTO.setLastName("Potter");
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(CustomerController.BASE_URL + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Harry")))
                .andExpect(jsonPath("$.lastName", equalTo("Potter")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Create a new customer")
    void createCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Harry");
        customer.setLastName("Potter");
        CustomerDTO returnedCustomer = new CustomerDTO();
        returnedCustomer.setFirstName(customer.getFirstName());
        returnedCustomer.setLastName(customer.getLastName());
        returnedCustomer.setId(2L);

        when(customerService.createNewCustomer(any())).thenReturn(returnedCustomer);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.firstName", equalTo("Harry")))
                .andExpect(jsonPath("$.lastName", equalTo("Potter")));
    }

    @Test
    @DisplayName("Update an existing customer")
    void updateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Harry");
        customer.setLastName("Potter");
        CustomerDTO returnedCustomer = new CustomerDTO();
        returnedCustomer.setFirstName(customer.getFirstName());
        returnedCustomer.setLastName(customer.getLastName());
        returnedCustomer.setId(2L);

        when(customerService.createOrUpdate(anyLong(), any())).thenReturn(returnedCustomer);

        mockMvc.perform(put(CustomerController.BASE_URL + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.firstName", equalTo(customer.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(customer.getLastName())));

    }


}