package br.com.rafaellbarros.openshiftpipeline.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by:
 *
 * @author rafael barros for DevDusCorre <rafaelbarros.softwareengineer@gmail.com> on 19/08/2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloRest {

    @GetMapping
    public ResponseEntity<String> init() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping(path = "/world", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> world() {
        return ResponseEntity.ok("Hello World OpenShift Pipepline!");
    }

    @GetMapping(path = "/world/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> worldName(@PathVariable String name) {
        String reqName = name == null || name == "" ? "Xablau" : name;
        return ResponseEntity.ok("Hello World OpenShift Pipepline " + reqName.toUpperCase()+ "!!!");
    }
}
