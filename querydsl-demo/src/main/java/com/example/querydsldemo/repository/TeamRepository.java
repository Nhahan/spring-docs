package com.example.querydsldemo.repository;

import com.example.querydsldemo.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
