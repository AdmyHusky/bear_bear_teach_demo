package com.example.bear_bear_teach_demo.service;

import com.example.bear_bear_teach_demo.Repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BearUserService {
    
    @Autowired
    private BearUserRepository bearUserRepository;

    public void addBearUser(BearUser bearUser) throws Exception {
        if (bearUser.getFirstName() == null || bearUser.getFirstName().isEmpty()) {
            throw new Exception("First name must be null");
        } else if (bearUser.getLastName() == null || bearUser.getLastName().isEmpty()) {
            throw new Exception("Last name must be null");
        } else if (bearUser.getAge() <= 0) {
            throw new Exception("Age can be a positive integer");
        }
        bearUserRepository.save(bearUser);
    }

    public BearUser findByBearId(Long id) {
        BearUser bearUser = bearUserRepository.findById(id).get();
        return bearUser != null ? bearUser : null;
    }

    public boolean deleteBearUser(Long id) {
        try {
            bearUserRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            System.out.println("Cannot delete by id: " + id);
            return false;
        }

    }

    public BearUser updateBearUser( BearUser bearUserRq) throws Exception {
        BearUser findBearUser = bearUserRepository.findById(bearUserRq.getId()).get();
        return findBearUser != null ? bearUserRepository.save(bearUserRq) : null;
    }

    public List<BearUser> findlistAll() {
        return bearUserRepository.findAll();
    }
}
