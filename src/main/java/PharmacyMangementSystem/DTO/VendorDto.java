package PharmacyMangementSystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import PharmacyMangementSystem.Model.Manufacturer;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VendorDto {

    private int vendorId;
    
    @NotEmpty(message = "must not be empty")
	private String vendorName;
	
	@NotEmpty(message = "must not be empty")
	private String vendorType;
	
//	@NotEmpty(message = "must not be empty")
	private int manufacturerId;
	
	@NotEmpty(message = "must not be empty")
	private String productDetails;
	
	@Pattern(regexp = "^[0-9]+$", message = "Contact must contain only numbers")
	@NotEmpty(message = "must not be empty")
	private String payment;
	
	@NotEmpty(message = "must not be empty")
	private String address;
	
	@Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only alphabetic characters")
	@NotEmpty(message = "must not be empty")
	private String businessName;
	
	@NotEmpty(message = "must not be empty")
	private String businessAddress;
	
	@Email
	@NotEmpty(message = "must not be empty")
	private String email;
	
	@Pattern(regexp = "^[0-9]+$", message = "Contact must contain only numbers")
	private String nationalTaxNumber;
	
	@Pattern(regexp = "^[0-9]+$", message = "Contact must contain only numbers")
	@NotEmpty(message = "must not be empty")
	private String salesTaxRegistrationNumber;
	
	@Pattern(regexp="(\\+92|0)[0-9]{10}", message = "Must start with '0'(11 digits) or '+92'(12 digits)")
	@Size(min=11,max = 13)	
	private String contactNumber;
	
	@Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only alphabetic characters")
	@NotEmpty(message = "must not be empty")
	private String status;
	
	@NotEmpty(message = "must not be empty")
	@JsonFormat(pattern = "yyyy-MM-dd")	
	private String registrationDate;

}
