package pe.edu.vallegrande.vg_ms_product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("sale")
public class Sale {
    
    @Id
    private Long id;
    
    @Column("sale_date")
    private LocalDate saleDate;
    
    @Column("name")
    private String name;
    
    @Column("ruc")
    private String ruc;
    
    @Column("address")
    private String address;
    
    @Column("product_id")
    private Integer productId;
    
    @Column("weight")
    private BigDecimal weight;
    
    @Column("packages")
    private Integer packages;
    
    @Column("total_weight")
    private BigDecimal totalWeight;
    
    @Column("price_per_kg")
    private BigDecimal pricePerKg;
    
    @Column("total_price")
    private BigDecimal totalPrice;
}
