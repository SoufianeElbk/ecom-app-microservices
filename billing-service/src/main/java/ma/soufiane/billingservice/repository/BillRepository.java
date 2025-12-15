package ma.soufiane.billingservice.repository;

import ma.soufiane.billingservice.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository  extends JpaRepository<Bill, Long> {
}