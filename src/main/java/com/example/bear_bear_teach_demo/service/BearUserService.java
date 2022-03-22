package com.example.bear_bear_teach_demo.service;

import com.example.bear_bear_teach_demo.exception.NotFoundException;
import com.example.bear_bear_teach_demo.repository.BearUserRepository;
import com.example.bear_bear_teach_demo.model.BearUser;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BearUserService {

    private final BearUserRepository bearUserRepository;

    public void addBearUser(BearUser bearUser) {
        if (bearUser.getFirstName() == null || bearUser.getFirstName().isBlank()) {
            throw new NotFoundException("First name must be null");
        } else if (bearUser.getLastName() == null || bearUser.getLastName().isBlank()) {
            throw new NotFoundException("Last name must be null");
        } else if (bearUser.getAge() <= 0) {
            throw new NotFoundException("Age can be a positive integer");
        }
        bearUserRepository.save(bearUser);
    }

    public BearUser findByBearId(Long id) {
        return bearUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("findByBearId Service does not exist for id = %s!", id)));
    }

    public boolean deleteBearUser(Long id) throws DataAccessException {
        try {
            if (id != null) {
                bearUserRepository.deleteById(id);
                return true;
            }
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Don't have id: " + id + " to delete");
        }
        return false;
    }

    public BearUser updateBearUser( BearUser bearUserRq) {
        BearUser findBearUser = bearUserRepository.findById(bearUserRq.getId())
                .orElseThrow(() -> new NotFoundException(String.format("updateBearUser Service does not exist for id = %s!", bearUserRq.getId())));;
        return findBearUser != null ? bearUserRepository.save(bearUserRq) : null;
    }

    public List<BearUser> findAll() {
        return bearUserRepository.findAll();
    }
}
