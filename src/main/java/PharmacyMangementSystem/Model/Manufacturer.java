package PharmacyMangementSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Manufacturer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int manufacturerId;
	
	@NotEmpty(message = "must not be empty")
	private String manufacturerName;
	
	@NotEmpty(message = "must not be empty")
	@Pattern(regexp="(\\+92|0)[0-9]{10}", message = "Must start with '0'(11 digits) or '+92'(12 digits)")
	@Size(min=11,max = 13)
	private String manufacturerContactNumber;


	@NotEmpty(message = "must not be empty")
	private String manufacturerAddress;
	
	@NotEmpty(message = "must not be empty")
	@Email
	private String manufacturerEmail;
	
	@NotEmpty(message = "must not be empty")
	@Pattern(regexp="(\\+92|0)[0-9]{10}", message = "Must start with '0'(11 digits) or '+92'(12 digits)")
	@Size(min=11,max = 13)
	private String contactPerson;
	
	@NotEmpty(message = "must not be empty")
	private String contactPersonPosition;
	
	@NotEmpty(message = "must not be empty")
	private String city;
	
	@NotEmpty(message = "must not be empty")
	private String state;

}
