package PharmacyMangementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PharmacyMangementSystem.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
Product findByProductId(int productId);
}
