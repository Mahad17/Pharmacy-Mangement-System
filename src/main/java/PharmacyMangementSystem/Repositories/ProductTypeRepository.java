package PharmacyMangementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PharmacyMangementSystem.Model.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
	ProductType findByProductTypeId(int productTypeId);

}
