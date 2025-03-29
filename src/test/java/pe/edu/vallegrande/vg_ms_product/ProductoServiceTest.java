package pe.edu.vallegrande.vg_ms_product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.ProductoRepository;
import pe.edu.vallegrande.vg_ms_product.service.ProductoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private ProductoModel producto1;
    private ProductoModel producto2;
    private ProductoModel producto3;

    @BeforeEach
    void setUp() {
        producto1 = new ProductoModel(1L, "Gallinas ponedoras", "Gallinas de alta postura", new BigDecimal("1500"), 
                                      100, new BigDecimal("8.5"), 200, LocalDate.of(2025, 3, 10),
                                      LocalDate.of(2026, 3, 10), "A", 1L);

        producto2 = new ProductoModel(2L, "Pollos de engorde", "Pollos listos para mercado", new BigDecimal("2500"), 
                                      50, new BigDecimal("7.0"), 500, LocalDate.of(2025, 4, 15),
                                      LocalDate.of(2025, 10, 15), "A", 2L);

        producto3 = new ProductoModel(3L, "Alimento balanceado", "Alimento rico en nutrientes", new BigDecimal("10000"), 
                                      200, new BigDecimal("3.2"), 1000, LocalDate.of(2025, 5, 1),
                                      LocalDate.of(2025, 11, 1), "A", 3L);
    }

    @Test
    void createProduct() {
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(producto3));

        StepVerifier.create(productoService.createProduct(producto3))
                .expectNextMatches(producto -> producto.getId().equals(3L) && producto.getType().equals("Alimento balanceado"))
                .verifyComplete();
    }

    @Test
    void getAllProducts() {
        when(productoRepository.findAll()).thenReturn(Flux.just(producto1, producto2, producto3));

        StepVerifier.create(productoService.getAllProducts())
                .expectNext(producto1, producto2, producto3)
                .verifyComplete();
    }

    @Test
    void updateProduct() {
        ProductoModel updatedProduct = new ProductoModel(1L, "Gallinas ponedoras", "Gallinas de alta postura",
                                                         new BigDecimal("1500"), 100, new BigDecimal("9.0"), 300,
                                                         LocalDate.of(2025, 3, 10), LocalDate.of(2026, 3, 10),
                                                         "A", 1L);

        when(productoRepository.findById(1L)).thenReturn(Mono.just(producto1));
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productoService.updateProduct(1L, updatedProduct))
                .expectNextMatches(producto -> producto.getPricePerKg().equals(new BigDecimal("9.0")) && producto.getStock() == 300)
                .verifyComplete();
    }

    @Test
    void softDeleteProduct() {
        ProductoModel deletedProduct = new ProductoModel(2L, "Pollos de engorde", "Pollos listos para mercado",
                                                         new BigDecimal("2500"), 50, new BigDecimal("7.0"), 500,
                                                         LocalDate.of(2025, 4, 15), LocalDate.of(2025, 10, 15),
                                                         "I", 2L); // Estado cambiado a "I"

        when(productoRepository.findById(2L)).thenReturn(Mono.just(producto2));
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(deletedProduct));

        StepVerifier.create(productoService.softDeleteProduct(2L))
                .expectNextMatches(product -> product.getStatus().equals("I"))
                .verifyComplete();
    }
    
}

