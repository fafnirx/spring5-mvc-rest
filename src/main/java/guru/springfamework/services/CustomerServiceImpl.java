package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
