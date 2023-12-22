package PharmacyMangementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PharmacyMangementSystem.Model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	Vendor findByVendorId(int vendorId);
//	Vendor findById(int vendorId);


}
