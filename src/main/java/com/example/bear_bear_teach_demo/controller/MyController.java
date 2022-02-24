package com.example.bear_bear_teach_demo.controller;

import com.example.bear_bear_teach_demo.Repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import com.example.bear_bear_teach_demo.service.BearUserService;
import com.example.bear_bear_teach_demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bearBearTeach")
public class MyController {

    @Autowired
    private MyService myService;

    @Autowired
    private BearUserService bearUserService;

    @Autowired
    private BearUserRepository bearUserRepository;

    @GetMapping()
    @RequestMapping("/hiBear")
    public ResponseEntity hiBear () {
        return  ResponseEntity.ok().body("Hi Bear");
    }

    @GetMapping()
    public ResponseEntity calculatorAddNumber (@RequestParam int a, @RequestParam int b) {
        return  ResponseEntity.ok().body(myService.addCalculator(a,b));
    }

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
        try {
            return ResponseEntity.ok(bearUserService.findByBearId(id));
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
        return bearUserService.listAll();
    }

}
