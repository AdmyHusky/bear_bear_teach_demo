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

    //find All User
    @GetMapping("/bearUsers")
    public List<BearUser> bearUsers() {
        return bearUserService.findAll();
    }

    //find by id
    @GetMapping("/bearUsers/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
            return ResponseEntity.ok(bearUserService.findByBearId(id));
    }

    //insert user
    @PostMapping("/bearUsers")
    public ResponseEntity<?> insertBearUser (@RequestBody BearUser bearUser) throws Exception {
            bearUserService.addBearUser(bearUser);
            return ResponseEntity.ok("insert success");

    }

    //delete user
    @DeleteMapping("/bearUsers/{id}")
    public ResponseEntity<?> deleteBearUser (@PathVariable Long id) {
        return bearUserService.deleteBearUser(id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    //update user
    @PutMapping("/bearUsers")
    public ResponseEntity<?> updateBearUser (@RequestBody BearUser bearUser) throws Exception {
            BearUser updateBearUser = bearUserService.updateBearUser(bearUser);
            return ResponseEntity.ok(updateBearUser);
    }

}
