package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper mapper;
    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerMapper mapper, CustomerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return repository.findAll().stream().map(mapper::customerToCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByLastName(String lastName) {

        return mapper.customerToCustomerDTO(repository.findCustomerByLastName(lastName));
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        //todo: improve error handling here
        return repository.findById(id)
                .map(mapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }


    private CustomerDTO saveCustomer(Customer customer) {
        Customer savedCustomer = repository.save(customer);
        CustomerDTO savedCustomerDTO = mapper.customerToCustomerDTO(savedCustomer);
        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.customerDTOToCustomer(customerDTO);
        return saveCustomer(customer);
    }

    @Override
    public CustomerDTO createOrUpdate(Long id, CustomerDTO customerDTO) {
        Customer customer = mapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveCustomer(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return repository.findById(id).map( customer -> {
            if(customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }
            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }
            return mapper.customerToCustomerDTO(customer);
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
