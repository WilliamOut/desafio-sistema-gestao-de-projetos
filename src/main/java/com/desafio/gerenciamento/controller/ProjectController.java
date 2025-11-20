package com.desafio.gerenciamento.controller;

import com.desafio.gerenciamento.request.ProjectRequestDTO;
import com.desafio.gerenciamento.response.ProjectResponseDTO;
import com.desafio.gerenciamento.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projetos", description = "Endpoints para gerenciamento de projetos") // Nomeia a seção no Swagger
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(summary = "Criar novo projeto", description = "Cria um projeto novo")
    public ResponseEntity<ProjectResponseDTO> criarNovoProjeto(@Valid @RequestBody ProjectRequestDTO request) {
        ProjectResponseDTO novoProjeto = projectService.criarProjeto(request);
        return new ResponseEntity<>(novoProjeto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos", description = "Lista todos os projetos criados")
    public ResponseEntity<List<ProjectResponseDTO>> listarTodosProjetos() {
        List<ProjectResponseDTO> projects = projectService.listarProjetos();
        return new ResponseEntity<>(projects,HttpStatus.OK);
    }
}
