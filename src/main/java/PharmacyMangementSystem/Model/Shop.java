package PharmacyMangementSystem.Model;

//import java.util.Collection;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

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
public class Shop{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int shopId;

	@NotEmpty(message = "must not be empty")
	private String shopName;
	
	@NotEmpty(message = "must not be empty")
	private String shopDescription;

	@NotEmpty(message = "must not be empty")
	@Email
	private String email;
	
	@NotEmpty(message = "must not be empty")
	private String password;
	
	@NotEmpty(message = "must not be empty")
	@Pattern(regexp="(\\+92|0)[0-9]{10}", message = "Must start with '0'(11 digits) or '+92'(12 digits)")
	@Size(min=11,max = 13)
	private String phoneNumber;

	@NotEmpty(message = "must not be empty")
	private String address;
	
	@NotEmpty(message = "must not be empty")
	private String registrationNumber;

}
