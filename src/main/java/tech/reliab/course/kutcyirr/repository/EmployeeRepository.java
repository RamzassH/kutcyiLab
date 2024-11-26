package tech.reliab.course.kutcyirr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.kutcyirr.entity.Employee;
import java.util.Optional;
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findById(int id);
    void deleteById(int id);
}