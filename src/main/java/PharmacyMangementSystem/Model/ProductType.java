package PharmacyMangementSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class ProductType {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)	
private int productTypeId;

@OneToOne
@JoinColumn(name = "manufacturerId")
private Manufacturer manufacturer;

@NotEmpty(message = "must not be empty")
private String productType;

}
