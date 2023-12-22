package PharmacyMangementSystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int vendorId;
	
//	@NotEmpty(message = "must not be empty")
	private String vendorName;
	
//	@NotEmpty(message = "must not be empty")
	private String vendorType;
	
	@OneToOne
	@JoinColumn(name = "manufacturerId")
	private Manufacturer manufacturer;
	
//	@NotEmpty(message = "must not be empty")
	private String productDetails;
	
//	@Pattern(regexp = "^\"\\d+\"$", message = "Input must be a string containing only numbers within double quotes")
//	@NotEmpty(message = "must not be empty")
	private String payment;
	
//	@NotEmpty(message = "must not be empty")
	private String address;
	
//	@Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only alphabetic characters")
//	@NotEmpty(message = "must not be empty")
	private String businessName;
	
//	@NotEmpty(message = "must not be empty")
	private String businessAddress;
	
//	@Email
//	@NotEmpty(message = "must not be empty")
	private String email;
	
//	@Pattern(regexp = "^[0-9]+$", message = "Contact must contain only numbers")	
	private String nationalTaxNumber;
	
//	@Pattern(regexp = "^\"\\d+\"$", message = "Input must be a string containing only numbers within double quotes")
//	@NotEmpty(message = "must not be empty")
	private String salesTaxRegistrationNumber;
	
//	@Pattern(regexp="(\\+92|0)[0-9]{10}", message = "Must start with '0'(11 digits) or '+92'(12 digits)")
//	@Size(min=11,max = 13)	
	private String contactNumber;
	
//	@Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only alphabetic characters")
//	@NotEmpty(message = "must not be empty")
	private String status;
	
//	@NotEmpty(message = "must not be empty")
//	@JsonFormat(pattern = "yyyy-MM-dd")	
	private String registrationDate;


	
}
