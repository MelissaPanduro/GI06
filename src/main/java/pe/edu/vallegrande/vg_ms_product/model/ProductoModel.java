package pe.edu.vallegrande.vg_ms_product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data

@Table("product")
public class ProductoModel {

    @Id
    private Long id;

    private String type;

    private String description;

    @Column("package_weight")
    private BigDecimal packageWeight;

    @Column("package_quantity")
    private Integer packageQuantity;

    @Column("price_per_kg")
    private BigDecimal pricePerKg;

    private Integer stock;

    @Column("entry_date")
    private LocalDate entryDate;

    @Column("expiry_date")
    private LocalDate expiryDate;

    private String status = "A";

    @Column("supplier_id")
    private Long supplierId;
}
