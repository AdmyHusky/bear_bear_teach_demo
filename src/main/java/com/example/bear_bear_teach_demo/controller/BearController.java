package com.example.bear_bear_teach_demo.controller;

import com.example.bear_bear_teach_demo.Repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import com.example.bear_bear_teach_demo.service.BearUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bear")
public class BearController {

    @Autowired
    private BearUserService bearUserService;

    @Autowired
    private BearUserRepository bearUserRepository;

    //insert user
    @PostMapping()
    public ResponseEntity<?> insertBearUser (@RequestBody BearUser bearUser) {
        try {
            bearUserService.addBearUser(bearUser);
            return ResponseEntity.ok("insert success");
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("x-developer", "Admy");

        try {
            return ResponseEntity.ok(bearUserService.findByBearId(id));
//                 ResponseEntity.ok().headers(headers).body(bearUserService.findByBearId(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBearUser (@PathVariable Long id) {
        return bearUserService.deleteBearUser(id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    //update user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBearUser (@PathVariable Long id,
                                             @RequestBody BearUser bearUser) {
        try {
            BearUser updateBearUser = bearUserService.updateBearUser(id,bearUser);
            return ResponseEntity.ok(updateBearUser);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    //find All User
    @GetMapping("/bearUsers")
    public List<BearUser> list() {
        return bearUserService.findlistAll();
    }

}
