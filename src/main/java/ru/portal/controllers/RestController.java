/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.controllers;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.metamodel.EntityType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.DeferredResult;
import ru.portal.interfaces.PortalTable;
import ru.portal.services.TableService;
import ru.portal.services.UserService;

@Controller
public class RestController {

    @Autowired
    private UserService userService;
    @Autowired
    private TableService tableService;

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
/**
 *  Возвращаем все таблицы у которых есть аннотация PortalTable
 * @param isTable
 * @param id
 * @return
 * @throws JSONException 
 */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = {"/tables"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> tables(
            @RequestParam(value = "Table", required = false) Boolean isTable,
            @RequestParam(value = "id", required = false) String id
    ) throws JSONException {
        
           HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(generateTreeJson(isTable, id), headers, HttpStatus.OK);
        
      
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handelExceptions(Exception e) {
        String result = e.getMessage();
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    
    
     private String generateTreeJson(Boolean isTable, String id) throws JSONException {
        JSONObject root = new JSONObject();
        JSONArray records = new JSONArray();

        root.put("records", records);

        Set<EntityType<?>> set = tableService.getEntityTypesByAnnotationClass(PortalTable.class);

        if (id == null) {
            for (EntityType<?> entityType : set) {
                JSONObject tableObject = new JSONObject();
                tableObject.put("table", true);
                tableObject.put("name", entityType.getBindableJavaType().getAnnotation(PortalTable.class).title());
                tableObject.put("id", entityType.getBindableJavaType().getName());
                records.put(tableObject);
            }
        } else {
            for (EntityType<?> entityType : set) {
                if (entityType.getBindableJavaType().getName().equals(id)) {
                    Field[] fields = entityType.getBindableJavaType().getDeclaredFields();
                    for (Field field : fields) {
                        JSONObject tableObject = new JSONObject();
                        tableObject.put("table", false);
                        tableObject.put("name",field.getName());
                        tableObject.put("id", field);
                        records.put(tableObject);
                    }
                }
            }
        }
        return root.toString();
    }

}
