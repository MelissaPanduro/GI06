package pe.edu.vallegrande.vg_ms_product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.ProductoRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<ProductoModel> createProduct(ProductoModel product) {
        return productoRepository.save(product);
    }

    public Flux<ProductoModel> getAllProducts() {
        return productoRepository.findAll();
    }

    public Mono<Void> deleteProduct(Long id) {
        return productoRepository.deleteById(id);
    }

    public Mono<ProductoModel> softDeleteProduct(Long id) {
        return productoRepository.findById(id)
                .flatMap(product -> {
                    product.setStatus("I"); // "I" de Inactivo
                    return productoRepository.save(product);
                });
    }

    public Mono<ProductoModel> restoreProduct(Long id) {
        return productoRepository.findByIdAndStatus(id, "I")
                .flatMap(product -> {
                    product.setStatus("A"); // "A" de Activo
                    return productoRepository.save(product);
                });
    }

    public Mono<ProductoModel> updateProduct(Long id, ProductoModel productDetails) {
        return productoRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setType(productDetails.getType());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPackageWeight(productDetails.getPackageWeight());
                    existingProduct.setPackageQuantity(productDetails.getPackageQuantity());
                    existingProduct.setPricePerKg(productDetails.getPricePerKg());
                    existingProduct.setStock(productDetails.getStock());
                    existingProduct.setEntryDate(productDetails.getEntryDate());
                    existingProduct.setExpiryDate(productDetails.getExpiryDate());
                    existingProduct.setSupplierId(productDetails.getSupplierId());
                    return productoRepository.save(existingProduct);
                });
    }
}
