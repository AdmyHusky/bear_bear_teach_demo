package com.example.bear_bear_teach_demo.repository;

import com.example.bear_bear_teach_demo.model.BearUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BearUserRepository extends JpaRepository<BearUser, Long> {
}
