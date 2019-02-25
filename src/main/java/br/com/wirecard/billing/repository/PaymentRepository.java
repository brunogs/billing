package br.com.wirecard.billing.repository;

import br.com.wirecard.billing.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
