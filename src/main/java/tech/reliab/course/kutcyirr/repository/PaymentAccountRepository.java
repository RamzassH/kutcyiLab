package tech.reliab.course.kutcyirr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.kutcyirr.entity.PaymentAccount;
import java.util.Optional;
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {
    Optional<PaymentAccount> findById(int id);
    void deleteById(int id);
}