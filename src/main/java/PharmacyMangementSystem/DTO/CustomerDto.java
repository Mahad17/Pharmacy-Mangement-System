package PharmacyMangementSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

	private int customerId;
	
	@NotEmpty(message = "must not be empty")
	private String customerName;
	
	@NotEmpty(message = "must not be empty")
	private String gender;
	
	@NotEmpty(message = "must not be empty")
	@Pattern(regexp="(\\+92|0)[0-9]{10}", message = "Must start with '0'(11 digits) or '+92'(12 digits)")
	@Size(min=11,max = 13)	
	private String contactNumber;
	
	@Email
	@NotEmpty(message = "must not be empty")
	private String email;
	
	@NotEmpty(message = "must not be empty")
	private String city;

	private String address;
	
	private String prescription;
	
	private String medicalCondition;
	
	private String insuranceDetails;

	
}
