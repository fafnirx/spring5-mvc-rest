package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createFruits();
        createCustomers();
        System.out.printf("Data loaded on Bootstrap: %d categories and %d customers\n",
                categoryRepository.count(),
                customerRepository.count());
    }

    private void createFruits() {
        Category fruits = new Category();
        fruits.setName("Fruits");
        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
    }

    private void createCustomers() {
        Customer harry = new Customer();
        harry.setFirstName("Harry");
        harry.setLastName("Potter");

        Customer hermione = new Customer();
        hermione.setFirstName("Hermione");
        hermione.setLastName("Granger");

        customerRepository.save(harry);
        customerRepository.save(hermione);
    }
}
