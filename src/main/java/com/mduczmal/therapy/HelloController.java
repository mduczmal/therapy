package com.mduczmal.therapy;

import com.mduczmal.therapy.user.Person;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@Controller
public class HelloController {

    @GetMapping(value = {"/hello", "/v2/**"})
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> data() {
        return Collections.singletonMap("response", "hi, react!");
    }

    @PostMapping(value = "/v2/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> person(@RequestBody Person person) {
        System.out.println("Person:");
        System.out.println(person.getName());
        System.out.println(person.getSurname());
        System.out.println(person.getEmail());
        return Collections.singletonMap("result", "ok!");
    }

}
