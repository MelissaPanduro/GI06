package pe.edu.vallegrande.vg_ms_product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.ProductoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private ProductoModel productoStub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productoStub = new ProductoModel();
        productoStub.setId(1L);
        productoStub.setType("Alimento");
        productoStub.setDescription("Harina de trigo");
        productoStub.setPackageWeight(50.0);
        productoStub.setPackageQuantity(10);
        productoStub.setPricePerKg(2.5);
        productoStub.setStock(100);
        productoStub.setEntryDate("2024-03-23");
        productoStub.setExpiryDate("2025-03-23");
        productoStub.setSupplierId(2L);
        productoStub.setStatus("A"); // Activo
    }

    @Test
    void testCreateProduct() {
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(productoStub));

        Mono<ProductoModel> result = productoService.createProduct(productoStub);

        assertNotNull(result);
        assertEquals("Harina de trigo", result.block().getDescription());
        verify(productoRepository, times(1)).save(any(ProductoModel.class));
    }

    @Test
    void testGetAllProducts() {
        when(productoRepository.findAll()).thenReturn(Flux.just(productoStub));

        Flux<ProductoModel> result = productoService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.count().block());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct() {
        when(productoRepository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = productoService.deleteProduct(1L);

        assertNotNull(result);
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSoftDeleteProduct() {
        when(productoRepository.findById(1L)).thenReturn(Mono.just(productoStub));
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(productoStub));

        Mono<ProductoModel> result = productoService.softDeleteProduct(1L);

        assertNotNull(result);
        assertEquals("I", result.block().getStatus());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(ProductoModel.class));
    }

    @Test
    void testRestoreProduct() {
        when(productoRepository.findByIdAndStatus(1L, "I")).thenReturn(Mono.just(productoStub));
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(productoStub));

        Mono<ProductoModel> result = productoService.restoreProduct(1L);

        assertNotNull(result);
        assertEquals("A", result.block().getStatus());
        verify(productoRepository, times(1)).findByIdAndStatus(1L, "I");
        verify(productoRepository, times(1)).save(any(ProductoModel.class));
    }

    @Test
    void testUpdateProduct() {
        ProductoModel updatedProduct = new ProductoModel();
        updatedProduct.setType("Bebida");
        updatedProduct.setDescription("Jugo de naranja");
        updatedProduct.setPackageWeight(1.5);
        updatedProduct.setPackageQuantity(6);
        updatedProduct.setPricePerKg(3.0);
        updatedProduct.setStock(50);
        updatedProduct.setEntryDate("2024-04-01");
        updatedProduct.setExpiryDate("2025-04-01");
        updatedProduct.setSupplierId(3L);

        when(productoRepository.findById(1L)).thenReturn(Mono.just(productoStub));
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(updatedProduct));

        Mono<ProductoModel> result = productoService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("Jugo de naranja", result.block().getDescription());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(ProductoModel.class));
    }
}
