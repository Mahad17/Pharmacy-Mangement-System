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

import PharmacyMangementSystem.DTO.ManufacturerDto;
import PharmacyMangementSystem.Model.Manufacturer;
import PharmacyMangementSystem.Repositories.ManufacturerRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.ManufacturerServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

	@Autowired
	ManufacturerServiceImpl manufacturerService ;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;

	@PostMapping("/post")
	public ResponseHandler postManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
		Manufacturer manufacturerPost= manufacturerService.postManufacturer(manufacturer);
		if(manufacturerPost!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",manufacturerPost) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	@GetMapping("/get/{manufacturerId}")
	public ResponseHandler getManufacturer(@PathVariable("manufacturerId") int manufacturerId) {
		ManufacturerDto manufacturer= manufacturerService.getManufacturer(manufacturerId);
		if(manufacturer!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + manufacturerId ,manufacturer) ;

		}else {
			return new ResponseHandler(0,"No Data available on id : " + manufacturerId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
		List<Manufacturer> manufacturer= manufacturerService.getAllManufacturer();
		if(manufacturer.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);

		}else {
			return new ResponseHandler(1,"All Data has been fetched" ,manufacturer) ;
		}
	}
	
	@DeleteMapping("/delete/{manufacturerId}")
	public ResponseHandler deleteDetails(@PathVariable int manufacturerId) {
		Manufacturer manufacturer=manufacturerRepository.findByManufacturerId(manufacturerId);
				if(manufacturer!=null) {
					manufacturerService.deleteManufacturer(manufacturerId);

			return new ResponseHandler(1,"Data has been Deleted of id : " + manufacturerId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : "+manufacturerId,null);
			}			
	}
	
	@PutMapping("/update/{manufacturerId}")  
	 public ResponseHandler updateData(@PathVariable int manufacturerId,@Valid @RequestBody Manufacturer manufacturer) {
	      Manufacturer manufacturerSave= manufacturerService.updateData(manufacturerId, manufacturer);
	      if (manufacturerSave!=null) {
			
		return new ResponseHandler(1,"Data has been updated of id : " + manufacturerId,manufacturerSave) ;
		}
	    else {
			return new ResponseHandler(0,"Error ! Id is not updated of id : " + manufacturerId,null);
		}
	    }

}
