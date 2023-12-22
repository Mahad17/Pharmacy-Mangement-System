package PharmacyMangementSystem.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PharmacyMangementSystem.DTO.CustomerDto;
import PharmacyMangementSystem.Model.Customer;
import PharmacyMangementSystem.Repositories.CustomerRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.CustomerServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerServiceImpl customerService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@PostMapping("/post")
	public ResponseHandler postCustomer(@Valid @RequestBody CustomerDto customer) {
		System.out.println(customer);
		Customer custom= customerService.postCustomer(customer);
		if(custom!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",custom) ;
		}
		else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	@GetMapping("/get/{customerId}")
	public ResponseHandler getDetails(@PathVariable("customerId") int customerId) {
		CustomerDto customer= customerService.getCustomer(customerId);
		if(customer!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + customerId ,customer) ;
		}
		else {
			return new ResponseHandler(0,"No Data available on id : "+customerId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
		List<Customer> customers= customerService.getAllCustomers();
		if(customers.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);
		}
		else {
			return new ResponseHandler(1,"All Data has been fetched" ,customers) ;
		}
	}
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseHandler deleteDetails(@PathVariable int customerId) {
		Customer customer=customerRepository.findByCustomerId(customerId);
				if(customer!=null) {
					 customerService.deleteCustomer(customerId);
			return new ResponseHandler(1,"Data has been Deleted of id : " + customerId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : "+customerId,null);
			}
	}
	
	@PutMapping("/update/{customerId}")  
	 public ResponseHandler updateData(@PathVariable int customerId,@Valid @RequestBody Customer customer) {
	   Customer customerUpdate=customerService.updateData(customerId, customer);
	   if (customerUpdate!=null) {	
		return new ResponseHandler(1,"Data has been updated of id : " + customerId,customerUpdate) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Data is not updated of id : " +customerId,null);
		}
	    }
}
