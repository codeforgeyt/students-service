package students.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import students.models.Student;
import students.repositories.StudentRepository;

@RequestMapping("/api")
@RestController
public class StudentController {
	
	@Autowired
	StudentRepository repository;
	
	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String getHelloWorld() {
		String message = "Hello World!";
		return message;
	}
	
	@RequestMapping(value = "/allStudents", method = RequestMethod.GET)
	public Iterable<Student> getAllStudents(){
		Iterable<Student> studentsCollection = repository.findAll();
		return studentsCollection;
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.GET, params = { "id" })
	public Student getStudent(@RequestParam(value = "id") Long id) {
		Student student = new Student();
		Optional<Student> optionalStudent = repository.findById(id);
		if(optionalStudent.isPresent()) {
			student = optionalStudent.get();
		}
		return student;
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.POST)
	public Student addStudent(@RequestParam(value="firstname") String firstname,
			@RequestParam(value="lastname") String lastname) {
		Student newStudent = new Student(firstname, lastname);
		
		repository.save(newStudent);
		
		return newStudent;
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.DELETE)
	public Student deleteStudent(@RequestParam(value = "id") Long id) {
		Student studentToDelete = new Student();
		Optional<Student> optionalStudent = repository.findById(id);
		if(optionalStudent.isPresent()) {
			studentToDelete = optionalStudent.get();
			repository.deleteById(studentToDelete.getId());
		}
		return studentToDelete;
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.PUT)
	public Student editStudent(@RequestParam(value = "id") Long id,
			@RequestParam(value="firstname", required = false) String firstname,
			@RequestParam(value="lastname", required = false) String lastname) {
		Optional<Student> optionalStudent = repository.findById(id);
		Student student = new Student();
		if(optionalStudent.isPresent()) {
			student = optionalStudent.get();
			if(firstname != null && student.getFirstname() != firstname) {
				student.setFirstname(firstname);
			}
			if(lastname != null && student.getLastname() != lastname) {
				student.setLastname(lastname);
			}
			repository.save(student);
		}
		return student;
	}

}