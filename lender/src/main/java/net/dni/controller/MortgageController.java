package net.dni.controller;

import lombok.extern.slf4j.Slf4j;
import net.dni.Mortgage;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/mortgage")
public class MortgageController {

    private RestTemplate restTemplate = new RestTemplate();
    private Map<String, Mortgage> localStorage = new HashMap<>();

    @CrossOrigin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Mortgage> register(@RequestBody Mortgage request) {
        String id = String.valueOf(localStorage.size() + 1);
        String fullName = request.getFullName();
        Mortgage mortgage = new Mortgage(id, fullName, false);

        boolean passValidation = Boolean.valueOf(restTemplate.getForObject("http://localhost:8082/credit?name=" + fullName, String.class));
        mortgage.setPass(passValidation);
        localStorage.put(id, mortgage);

        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).get(mortgage.getMortgageId()));
        URI uri = linkBuilder.toUri();

        if (mortgage.isPass()) {
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(uri.getScheme()).host(uri.getHost()).port(uri.getPort())
                    .path("/ui/certification.html")
                    .queryParam("id", id).build();
            Link link = new Link(uriComponents.toUriString(), "next");
            mortgage.add(link);
        } else {
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(uri.getScheme()).host(uri.getHost()).port(uri.getPort())
                    .path("/ui/resolution.html")
                    .queryParam("id", id).build();
            Link link = new Link(uriComponents.toUriString(), "next");
            mortgage.add(link);
        }

        return new ResponseEntity<>(mortgage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Mortgage> get(@PathVariable(name = "id") String id) {
        Mortgage mortgage = localStorage.get(id);
        if (mortgage == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mortgage, HttpStatus.OK);
        }
    }


    @PostMapping(path = "/{id}/certify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> certify(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/resolve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> resolve(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
