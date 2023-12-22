package PharmacyMangementSystem.DTO;

import lombok.Data;

@Data
public class ManufacturerDto {

	private int manufacturerId;
	private String manufacturerName;
	private String manufacturerContactNumber;
	private String manufacturerAddress;
	private String manufacturerEmail;
	private String contactPerson;
	private String contactPersonPosition;
	private String city;
	private String state;

}
