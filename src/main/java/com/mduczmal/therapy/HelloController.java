package com.mduczmal.therapy;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

}
