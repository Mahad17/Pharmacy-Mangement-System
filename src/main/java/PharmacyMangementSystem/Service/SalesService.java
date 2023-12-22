package PharmacyMangementSystem.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import PharmacyMangementSystem.DTO.SalesDto;
import PharmacyMangementSystem.Model.Sales;

@Service
public interface SalesService {
	
	public Sales postSales(SalesDto sales);	
	public SalesDto getSales(int salesId);
	public List<Sales> getAllSales();
	public Boolean deleteSales(int salesId);
	public Sales updateData(int salesId,Sales sales);

}
