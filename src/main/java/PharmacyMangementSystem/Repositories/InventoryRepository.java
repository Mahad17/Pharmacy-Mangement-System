package PharmacyMangementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PharmacyMangementSystem.Model.Inventory;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	Inventory findByInventoryId(int inventoryId);
}
