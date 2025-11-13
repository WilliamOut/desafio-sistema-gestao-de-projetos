package com.desafio.gerenciamento.service;

import com.desafio.gerenciamento.handler.ProjectExists;
import com.desafio.gerenciamento.handler.ResourceNotFoundException;
import com.desafio.gerenciamento.model.Project;
import com.desafio.gerenciamento.repository.ProjectRepository;
import com.desafio.gerenciamento.request.ProjectRequestDTO;
import com.desafio.gerenciamento.response.ProjectResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project criarProjeto(ProjectRequestDTO dto) {
        Project acharProjeto = projectRepository.findByName(dto.getName());
        if(acharProjeto != null) {
            throw new ProjectExists("Projeto com o nome " + dto.getName() + " já existe!");
        }
        Project novoProjeto = new Project();
        novoProjeto.setName(dto.getName());
        novoProjeto.setDescription(dto.getDescription());
        novoProjeto.setStartDate(dto.getStartDate());
        novoProjeto.setEndDate(dto.getEndDate());
        return projectRepository.save(novoProjeto);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> listarProjetos() {
        List<Project> pegarTodos = projectRepository.findAll();
        if(pegarTodos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum projeto encontrado!");
        }
        return pegarTodos.stream()
                .map(ProjectResponseDTO::new)
                .collect(Collectors.toList());
    }
}

