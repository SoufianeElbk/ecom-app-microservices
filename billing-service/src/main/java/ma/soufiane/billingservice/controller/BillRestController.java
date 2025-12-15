package ma.soufiane.billingservice.controller;

import ma.soufiane.billingservice.entity.Bill;
import ma.soufiane.billingservice.feign.CustomerRestClient;
import ma.soufiane.billingservice.feign.ProductRestClient;
import ma.soufiane.billingservice.repository.BillRepository;
import ma.soufiane.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bills")
public class BillRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerRestClient customerRestClient;
    @Autowired
    private ProductRestClient productRestClient;
    @GetMapping(path = "{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
        System.out.println(customerRestClient.getCustomerById(bill.getCustomerId()).getName());
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(productItem -> {
            productItem.setProduct(productRestClient.getProductById(productItem.getProductId()));
        });
        return bill;
    }
}