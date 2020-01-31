package com.movinator.movinator.repository;

import com.movinator.movinator.entity.UserChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChoiceRepository extends JpaRepository<UserChoice,Long> {
}