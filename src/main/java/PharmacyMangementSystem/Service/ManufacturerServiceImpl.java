package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.ManufacturerDto;
import PharmacyMangementSystem.Model.Manufacturer;
import PharmacyMangementSystem.Repositories.ManufacturerRepository;

@Service
public class ManufacturerServiceImpl implements ManufacturerService{
	
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public  Manufacturer postManufacturer(Manufacturer manufacturerDto) {
		Manufacturer manufacturer=modelMapper.map(manufacturerDto, Manufacturer.class);
		return manufacturerRepository.save(manufacturer); 
	}
	
	@Override
	public ManufacturerDto getManufacturer(int manufacturerId) {
		Manufacturer manufacturer=manufacturerRepository.findByManufacturerId(manufacturerId);
		if (manufacturer!=null) {
			ManufacturerDto manufacturerDto=modelMapper.map(manufacturer, ManufacturerDto.class);
			return manufacturerDto;
		}
		else {		
		return null;
		}
	}
	
	@Override
	public List<Manufacturer> getAllManufacturer(){
	return manufacturerRepository.findAll();
	}
	
	@Override
	public Boolean deleteManufacturer(int manufacturerId) {
		Manufacturer manufacturer= manufacturerRepository.findByManufacturerId(manufacturerId);
		manufacturerRepository.delete(manufacturer);
	 return true;
	}
	
	@Override
	 public Manufacturer updateData( int manufacturerId,Manufacturer manufacturer) {
		   Optional<Manufacturer> manufacturerFind = manufacturerRepository.findById(manufacturerId);
		   
		    if (manufacturerFind.isPresent()) {
		    	Manufacturer manufacturerGet = manufacturerFind.get();
		    	manufacturerGet.setCity(manufacturer.getCity());
		    	manufacturerGet.setContactPerson(manufacturer.getContactPerson());
		    	manufacturerGet.setContactPersonPosition(manufacturer.getContactPersonPosition());
		    	manufacturerGet.setManufacturerAddress(manufacturer.getManufacturerAddress());
		    	manufacturerGet.setManufacturerContactNumber(manufacturer.getManufacturerContactNumber());
		    	manufacturerGet.setManufacturerEmail(manufacturer.getManufacturerEmail());
		    	manufacturerGet.setManufacturerName(manufacturer.getManufacturerName());
		    	manufacturerGet.setState(manufacturer.getState());
		      return manufacturerRepository.save(manufacturerGet);
		    }
		    else {
				return null;
				}
		    }
}
