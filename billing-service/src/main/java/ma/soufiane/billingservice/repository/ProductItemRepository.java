package ma.soufiane.billingservice.repository;

import ma.soufiane.billingservice.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}