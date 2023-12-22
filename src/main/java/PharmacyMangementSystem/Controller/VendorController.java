package PharmacyMangementSystem.Controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PharmacyMangementSystem.DTO.VendorDto;
import PharmacyMangementSystem.Model.Manufacturer;
import PharmacyMangementSystem.Model.Vendor;
import PharmacyMangementSystem.Repositories.ManufacturerRepository;
import PharmacyMangementSystem.Repositories.VendorRepository;
import PharmacyMangementSystem.Response.ResponseHandler;
import PharmacyMangementSystem.Service.VendorServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
    VendorServiceImpl vendorService ;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
    VendorRepository vendorRepository;
	@Autowired
	ManufacturerRepository manufacturerRepository;
	@PostMapping("/post")
	public ResponseHandler postVendor(@Valid @RequestBody VendorDto vendor) {
		System.out.println(vendor);
		if(StringUtils.isEmpty(vendor.getAddress())||StringUtils.isEmpty(vendor.getBusinessAddress())||StringUtils.isEmpty(vendor.getBusinessName())||StringUtils.isEmpty(vendor.getContactNumber())||StringUtils.isEmpty(vendor.getEmail())||StringUtils.isEmpty(vendor.getNationalTaxNumber())||StringUtils.isEmpty(vendor.getPayment())||StringUtils.isEmpty(vendor.getProductDetails())||StringUtils.isEmpty(vendor.getRegistrationDate())||StringUtils.isEmpty(vendor.getSalesTaxRegistrationNumber())||StringUtils.isEmpty(vendor.getVendorName())||StringUtils.isEmpty(vendor.getVendorType())||StringUtils.isEmpty(vendor.getStatus())||vendor.getManufacturerId()==0){
			return new ResponseHandler(0,"Fields are Empty");		
		} 
		Manufacturer manufacturer=manufacturerRepository.findByManufacturerId(vendor.getManufacturerId());
		if(manufacturer==null) {
			return new ResponseHandler(0,"Manufacturer Not Found");		
		}
//		model?
		Vendor vendorPost= vendorService.postVendorDto(vendor);
		VendorDto vendorDto =modelMapper.map(vendorPost, VendorDto.class);
		if(vendorPost!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",vendorDto) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	@PostMapping("/postVendor")
	public ResponseHandler postVendor(@Valid @RequestBody Vendor vendor) {
		System.out.println(vendor);
		if(StringUtils.isEmpty(vendor.getAddress())||StringUtils.isEmpty(vendor.getBusinessAddress())||StringUtils.isEmpty(vendor.getBusinessName())||StringUtils.isEmpty(vendor.getContactNumber())||StringUtils.isEmpty(vendor.getEmail())||StringUtils.isEmpty(vendor.getNationalTaxNumber())||StringUtils.isEmpty(vendor.getPayment())||StringUtils.isEmpty(vendor.getProductDetails())||StringUtils.isEmpty(vendor.getRegistrationDate())||StringUtils.isEmpty(vendor.getSalesTaxRegistrationNumber())||StringUtils.isEmpty(vendor.getVendorName())||StringUtils.isEmpty(vendor.getVendorType())||StringUtils.isEmpty(vendor.getStatus())){
			return new ResponseHandler(0,"Fields are Empty");		
		} 
		Manufacturer manufacturer=manufacturerRepository.findByManufacturerId(vendor.getManufacturer().getManufacturerId());
		if(manufacturer==null) {
			return new ResponseHandler(0,"Manufacturer Not Found");		
		}
		Vendor vendorSave =vendorRepository.save(vendor);
//		Vendor vendorPost= vendorService.postVendorDto(vendor);
//		VendorDto vendorDto= modelMapper.map(vendorPost, VendorDto.class);
		if(vendorSave!=null) {
		return new ResponseHandler(1,"Data inserted Successfully",vendorSave) ;
		}else {
			return new ResponseHandler(0,"Error! Data Not inserted",null) ;
		}
	}
	
	
	@GetMapping("/get/{vendorId}")
	public ResponseHandler getDetails(@PathVariable int vendorId) {
		VendorDto vendor= vendorService.getVendor(vendorId);
		if(vendor!=null) {
			return new ResponseHandler(1,"Data has been fetched of id : " + vendorId ,vendor) ;
		}
		else {
			return new ResponseHandler(0,"No Data available on id : " + vendorId,null);
		}
	}
	
	@GetMapping("/get/all")
	public ResponseHandler getAll() {
		List<Vendor> vendors= vendorService.getAllVendor();
		if(vendors.isEmpty()) {
			return new ResponseHandler(0,"No Data available",null);

		}else {
			return new ResponseHandler(1,"All Data has been fetched" ,vendors) ;
		}
	}
	
//	@RequestMapping(path="/delete/{stockId}" ,method = RequestMethod.DELETE)
//	public String delete(@PathVariable("stockId") int stockId) {
//	stockServiceImpl.deleteStock(stockId);
//	return "medicines";
//	}
	
	@DeleteMapping("/delete/{vendorId}")
	public ResponseHandler deleteDetails(@PathVariable int vendorId) {
		Vendor vendor=vendorRepository.findByVendorId(vendorId);
				if(vendor!=null) {
					 vendorService.deleteVendor(vendorId);

			return new ResponseHandler(1,"Data has been Deleted of id : " + vendorId,true) ;
			}
				else {
				return new ResponseHandler(0,"No Data available on id : " + vendorId,null);
			}
	         }
	
	@PutMapping("/update/{vendorId}")  
	public ResponseHandler updateData(@PathVariable int vendorId ,@Valid @RequestBody Vendor vendor) {
		Vendor vendorUpdate=vendorService.updateData(vendorId, vendor);
		if(vendorUpdate!=null) {
			return new ResponseHandler(1,"Data has been updated of id : " + vendorId,vendorUpdate) ;
		}
		else {
			return new ResponseHandler(0,"Error Data is not updated of id : " + vendorId,vendorUpdate) ;
		}
	}	
}
