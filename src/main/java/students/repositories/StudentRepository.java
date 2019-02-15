package students.repositories;

import org.springframework.data.repository.CrudRepository;
import students.models.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}
