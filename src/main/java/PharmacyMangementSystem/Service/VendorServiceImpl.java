package PharmacyMangementSystem.Service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.VendorDto;
import PharmacyMangementSystem.Model.Manufacturer;
import PharmacyMangementSystem.Model.Vendor;
import PharmacyMangementSystem.Repositories.ManufacturerRepository;
import PharmacyMangementSystem.Repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService{
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@Override
	public  Vendor postVendorDto(VendorDto vendorDto) {
		
		Vendor vendor=modelMapper.map(vendorDto, Vendor.class);
		return vendorRepository.save(vendor); 
	}
//	public  Vendor postVendor(Vendor vendor) {
//		Manufacturer manufacturer= manufacturerRepository.findByManufacturerNameIgnoreCase(vendor.getManufacturer().getManufacturerName());
//		int manufacturerId=manufacturer.getManufacturerId();
//		VendorDto vendorDto= modelMapper.map(vendor, VendorDto.class);
//		vendorDto.setManufacturerId(manufacturerId);
//		Vendor vendorMap= modelMapper.map(vendorDto, Vendor.class);
//		return vendorRepository.save(vendorMap); 
		
//	}
	
	@Override
	public VendorDto getVendor(int vendorId) {
		Vendor vendor= vendorRepository.findByVendorId(vendorId);
	if (vendor!=null) {
		VendorDto vendorDto=modelMapper.map(vendor, VendorDto.class);
		return vendorDto;
	}
	else {
		return null;
	}
	}
	
	@Override
	public List<Vendor> getAllVendor(){
	return vendorRepository.findAll();	
	}
	
	@Override
	public Boolean deleteVendor(int vendorId) {
		Vendor  vendor= vendorRepository.findByVendorId(vendorId);
		vendorRepository.delete(vendor);
	 return true;
	}
	
	@Override
	public Vendor updateData( int vendorId, Vendor vendor) {
		   Optional<Vendor> vendorFind = vendorRepository.findById(vendorId);
		   
		    if (vendorFind.isPresent()) {
		    	Vendor vendorGet = vendorFind.get();
		    	Manufacturer manufacturer= manufacturerRepository.findByManufacturerId(vendor.getManufacturer().getManufacturerId());
		    	vendorGet.setAddress(vendor.getAddress());
		    	vendorGet.setBusinessAddress(vendor.getBusinessAddress());
		    	vendorGet.setBusinessName(vendor.getBusinessName());
		    	vendorGet.setContactNumber(vendor.getContactNumber());
		    	vendorGet.setEmail(vendor.getEmail());
		    	
		    	vendorGet.setManufacturer(manufacturer);
		    	vendorGet.setNationalTaxNumber(vendor.getNationalTaxNumber());
		    	vendorGet.setPayment(vendor.getPayment());
		    	vendorGet.setProductDetails(vendor.getProductDetails());
		    	vendorGet.setRegistrationDate(vendor.getRegistrationDate());
		    	vendorGet.setSalesTaxRegistrationNumber(vendor.getSalesTaxRegistrationNumber());
		    	vendorGet.setStatus(vendor.getStatus());
		    	vendorGet.setVendorName(vendor.getVendorName());
		    	vendorGet.setVendorType(vendor.getVendorType());
		        return vendorRepository.save(vendorGet);
		    }
		    else {
				return null;
				}
		    }	
	public Vendor update( int vendorId, VendorDto vendor) {
		   Optional<Vendor> vendorFind = vendorRepository.findById(vendorId);
		   
		    if (vendorFind.isPresent()) {
		    	Vendor vendorGet = vendorFind.get();
		    	Manufacturer manufacturer= manufacturerRepository.findByManufacturerId(vendor.getManufacturerId());
		    	vendorGet.setManufacturer(manufacturer);
		    	vendorGet.setAddress(vendor.getAddress());
		    	vendorGet.setBusinessAddress(vendor.getBusinessAddress());
		    	vendorGet.setBusinessName(vendor.getBusinessName());
		    	vendorGet.setContactNumber(vendor.getContactNumber());
		    	vendorGet.setEmail(vendor.getEmail());
		    	vendorGet.setNationalTaxNumber(vendor.getNationalTaxNumber());
		    	vendorGet.setPayment(vendor.getPayment());
		    	vendorGet.setProductDetails(vendor.getProductDetails());
		    	vendorGet.setRegistrationDate(vendor.getRegistrationDate());
		    	vendorGet.setSalesTaxRegistrationNumber(vendor.getSalesTaxRegistrationNumber());
		    	vendorGet.setStatus(vendor.getStatus());
		    	vendorGet.setVendorName(vendor.getVendorName());
		    	vendorGet.setVendorType(vendor.getVendorType());
		        return vendorRepository.save(vendorGet);
		    }
		    else {
				return null;
				}
		    }	

}
