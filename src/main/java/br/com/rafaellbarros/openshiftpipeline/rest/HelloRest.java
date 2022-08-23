package br.com.rafaellbarros.openshiftpipeline.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by:
 *
 * @author rafael barros for DevDusCorre <rafaelbarros.softwareengineer@gmail.com> on 23/08/2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloRest {

    @GetMapping(path = "/world", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> world() {
        return ResponseEntity.ok("Hello World OpenShift Pipepline!");
    }
}
