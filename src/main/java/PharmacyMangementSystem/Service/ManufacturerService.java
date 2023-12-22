package PharmacyMangementSystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.ManufacturerDto;
import PharmacyMangementSystem.Model.Manufacturer;

@Service
public interface ManufacturerService {
	
	public Manufacturer postManufacturer(Manufacturer manufacturerDto);
	
	public ManufacturerDto getManufacturer(int manufacturerId);

	public List<Manufacturer> getAllManufacturer();

	public Boolean deleteManufacturer(int manufacturerId);

	public Manufacturer updateData(int manufacturerId,Manufacturer manufacturer);
}
