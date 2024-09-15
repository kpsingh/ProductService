package com.lld4.productservice.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class SampleController {

    @GetMapping("/{username}/{userage}")
    public String hello(@PathVariable("username") String name, @PathVariable("userage") Integer age) {
        return "Hello " + name + ". Your age is " + age;
    }

    @PostMapping("")
    public String hello(@RequestBody SampleRequestDTO request) {
        StringBuilder sb = new StringBuilder("Hello ");
        sb.append(request.getName() + ", your age is ");
        sb.append(request.getAge());
        return sb.toString();
    }

    @PostMapping("/v1")
    public ResponseEntity<SampleResponseDTO> helloV1(@RequestBody SampleRequestDTO request){
        SampleResponseDTO response = new SampleResponseDTO();
        response.setAge(request.getAge());
        response.setName(request.getName());
        if (request.getAge() >= 18){
            response.setMessage("You are eligible to vote");
        }else {
            response.setMessage("You are not eligible to vote");
        }
        return ResponseEntity.ok(response);
    }
}
