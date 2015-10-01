/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.portal.config.controller;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;


@Controller
public class RestController {
    
    @RequestMapping(value = {"/main"})
    @ResponseBody
    public ResponseEntity<String> main() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=UTF-8");
        ResponseEntity responseEntity = new ResponseEntity<>("main", headers, HttpStatus.OK);

        return responseEntity;

    }
    
    
    
    @RequestMapping(value = {"/random/{id}"})
    @ResponseBody
    public DeferredResult<ResponseEntity<String>> random(@PathVariable Long id) {

        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();
        String result = String.format("random : %s", ThreadLocalRandom.current().nextLong(id));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=UTF-8");
        ResponseEntity responseEntity = new ResponseEntity<>(result, headers, HttpStatus.OK);

        deferredResult.setResult(responseEntity);

        return deferredResult;

    }
    
}
