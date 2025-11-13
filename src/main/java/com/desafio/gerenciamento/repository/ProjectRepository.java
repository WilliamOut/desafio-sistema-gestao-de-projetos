package com.desafio.gerenciamento.repository;

import com.desafio.gerenciamento.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Project findByName(String name);
}
