package com.desafio.gerenciamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.desafio.gerenciamento.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTitle(String title);

    @Query("SELECT t FROM Task t " +
            "WHERE UPPER(t.status) = UPPER(:status) " +
            "OR UPPER(t.priority) = UPPER(:priority) " +
            "OR t.project.id = :idproject ")
    List<Task> findByStatusOrPriorityOrIdProject(
            @Param("status") String status,
            @Param("priority") String priority,
            @Param("idproject") Long idProject);
}
