package PharmacyMangementSystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.CustomerDto;
import PharmacyMangementSystem.Model.Customer;

@Service
public interface CustomerService {
	
	public  Customer postCustomer(CustomerDto customer) ;
	
	public CustomerDto getCustomer(int customerId) ;
	
    public List<Customer> getAllCustomers();
    
    public Boolean deleteCustomer(int customerId) ;
    
    public Customer updateData(int customerId,Customer customer);


}
