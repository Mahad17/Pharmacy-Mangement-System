package PharmacyMangementSystem.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import PharmacyMangementSystem.Model.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

    @Query("SELECT s FROM Sales s ORDER BY s.salesId DESC")
	List<Sales> findAll();
    
    @Query("SELECT SUM(s.totalPrice) FROM Sales s")
    int sumTotalPrice();
    
	Sales findBySalesId(int salesId);
	
	List<Sales> findAllByInvoiceDate(String invoiceDate);
	
	Sales findByInvoiceNumber(int invoiceNumber);
	
	Sales findByItemName(String itemName);

}
