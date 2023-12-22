package PharmacyMangementSystem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import PharmacyMangementSystem.DTO.VendorDto;
import PharmacyMangementSystem.Model.Vendor;

@Service
public interface VendorService {
	
	public Vendor postVendorDto(VendorDto vendor);
	
	public VendorDto getVendor(int vendorId);

	public List<Vendor> getAllVendor();

	public Boolean deleteVendor(int vendorId);

	public Vendor updateData(int vendorId,Vendor vendor );
}
