package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.CustomerDto;
import PharmacyMangementSystem.Model.Customer;
import PharmacyMangementSystem.Repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public  Customer postCustomer(CustomerDto customer) {
		Customer customerMap=modelMapper.map(customer,Customer.class);
		return customerRepository.save(customerMap); 
	}
	
	
	@Override
	public CustomerDto getCustomer(int customerId) {
		Customer customer =customerRepository.findByCustomerId(customerId);
	if (customer!=null) {
		CustomerDto customerDto=modelMapper.map(customer, CustomerDto.class);
		return customerDto;		
	}
	else {
		return null;
	}
	}
	
	@Override
    public List<Customer> getAllCustomers(){
	return customerRepository.findAll();
	}
	
	@Override
    public Boolean deleteCustomer(int customerId) {
	Customer customer= customerRepository.findByCustomerId(customerId);
	customerRepository.delete(customer);
	return true;
	}
	
	public Boolean deleteCustomerByContactNumber(String contactNumber) {
		Customer customer= customerRepository.findByContactNumber(contactNumber);
		customerRepository.delete(customer);
		return true;
		}

	
	@Override
	public Customer updateData( int customerId, Customer customer) {
	Optional<Customer> customers = customerRepository.findById(customerId);

	if (customers.isPresent()) {
	     Customer customer2 = customers.get();
	     customer2.setCustomerName(customer.getCustomerName());
	     customer2.setCity(customer.getCity());
	     customer2.setAddress(customer.getAddress());
	     customer2.setEmail(customer.getEmail());
	     customer2.setContactNumber(customer.getContactNumber());
	     customer2.setGender(customer.getGender());
	     customer2.setInsuranceDetails(customer.getInsuranceDetails());
	     customer2.setMedicalCondition(customer.getMedicalCondition());
	     customer2.setPrescription(customer.getPrescription());	      
	     return customerRepository.save(customer2);
		     }
		    else {
				return null;
				}
	}
}