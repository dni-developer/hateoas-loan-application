package net.dni;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/credit")
public class CreditController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> post(@RequestParam(name = "name") String name) throws InterruptedException {

        log.info("check score for {}", name);

        Thread.sleep(5000);

        return new ResponseEntity<>(String.valueOf("pass".equalsIgnoreCase(name)), HttpStatus.OK);
    }
}
