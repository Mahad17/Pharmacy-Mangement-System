package PharmacyMangementSystem.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PharmacyMangementSystem.Model.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer> {
	Manufacturer findByManufacturerId(int manufacturerId);
	Manufacturer findByManufacturerNameIgnoreCase(String manufacturerName);

}
