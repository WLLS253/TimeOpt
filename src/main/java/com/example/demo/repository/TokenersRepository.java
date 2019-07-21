package com.example.demo.repository;

import com.example.demo.entity.Tokeners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenersRepository extends JpaRepository<Tokeners,Long> {

    public List<Tokeners> findByUuid(String uu);
}
