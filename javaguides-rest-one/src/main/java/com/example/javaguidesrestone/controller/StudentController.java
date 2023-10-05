package com.example.javaguidesrestone.controller;

import com.example.javaguidesrestone.bean.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("student")
public class StudentController {
    List<Student> students;

    @PostConstruct
    public void postConstruct() {
        //        ! REMEMBER
        //        * List.of can be replaced with Arrays.asList as well
        students = new ArrayList<>(
                List.of(
                        new Student(1, "stud", "1"),
                        new Student(2, "stud", "2"),
                        new Student(3, "stud", "3")
                ));
    }

    @GetMapping
    public Student getStudent() {
        return new Student(1, "stud", "one");
    }

    @GetMapping("list")
    public List<Student> getStudents() {
        return students;
    }

    //    ! path variables
    @GetMapping("{id}/{first-name}/{last-name}")
    public Student studentPathVariables(
            @PathVariable int id,
            @PathVariable("first-name") String firstname,
            @PathVariable("last-name") String lastname
    ) {
        //        return students.stream()
        //                       .filter(i -> i.getId() == id)
        //                       .findFirst()
        //                       .orElse(null);
        return new Student(id, firstname, lastname);
    }

    //    ! request params
    //    * => ?id=1&name=abc
    //    * http://localhost:8080/student/query?id=1&firstname=stud&lastname=1
    @GetMapping("query")
    public Student studentRequestParams(
            @RequestParam int id,
            @RequestParam String firstname,
            @RequestParam String lastname
    ) {
        //        return new Student(id, "s", "1");
        return students.stream()
                       .filter(i -> {
                           return i.getId() == id
                                   && i.getFirstname().equals(firstname)
                                   && i.getLastname().equals(lastname);
                       })
                       .findFirst()
                       .orElse(null);
        //                       .orElseThrow();
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        System.out.println(student);
        return student;
    }

    @PutMapping("update")
    public List<Student> update(@RequestBody Student student) {

        students.stream()
                .filter(i -> i.getId() == student.getId())
                .findFirst()
                .ifPresent(s -> {
                    //                    students = new ArrayList<>(students); // * to come over immutability
                    students.set(students.indexOf(s), student);
                    //                                        students.remove(1);
                });


        // ! READ
        //        * https://stackoverflow.com/questions/6137224/immutable-object-with-arraylist-member-variable-why-can-this-variable-be-chang
        //        List<Student> ss = new ArrayList<>(students);
        //        ss.add(student);
        //        ss.add(student);
        //        students = ss;
        //        this.students.add(student);

        //                .ifPresent(s -> students.set(3, student));
        //        return ss;
        return students;
    }

    @DeleteMapping("delete")
    public String delete(@RequestBody Student student) {
        AtomicReference<String> message = new AtomicReference<>("Student Not Found");

        students.stream()
                .filter(i -> i.getId() == student.getId())
                .findFirst()
                .ifPresent(s -> {
                    students.remove(s);
                    message.set("Student Deleted");
                });

        return message.get();
    }

    //    * https://medium.com/@daryl-goh/spring-boot-requestentity-vs-responseentity-requestbody-vs-responsebody-dc808fb0d86c
    @GetMapping("dummy-json")
    public ResponseEntity<String> dummyJson() {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> request = RequestEntity
                .get("https://dummyjson.com/products")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response;
    }


}
