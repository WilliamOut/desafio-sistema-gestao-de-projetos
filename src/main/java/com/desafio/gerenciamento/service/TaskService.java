package com.desafio.gerenciamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.gerenciamento.handler.ResourceNotFoundException;
import com.desafio.gerenciamento.handler.TaskExists;
import com.desafio.gerenciamento.model.Project;
import com.desafio.gerenciamento.model.Task;
import com.desafio.gerenciamento.repository.ProjectRepository;
import com.desafio.gerenciamento.repository.TaskRepository;
import com.desafio.gerenciamento.request.TaskRequestDTO;
import com.desafio.gerenciamento.response.TaskResponseDTO;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Task criarTask(TaskRequestDTO dto) {
        Task tituloTask = taskRepository.findByTitle(dto.getTitle());

        if (tituloTask != null) {
            throw new TaskExists("Essa task já existe!");
        }

        Project acharProjeto = projectRepository.findById(dto.getIdProject())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Projeto com ID " + dto.getIdProject() + " não encontrado"));

        Task novaTask = new Task();
        novaTask.setTitle(dto.getTitle());
        novaTask.setDescription(dto.getDescription());
        novaTask.setStatus(dto.getStatus());
        novaTask.setPriority(dto.getPriority());
        novaTask.setDueDate(dto.getDueDate());
        novaTask.setProject(acharProjeto);
        return taskRepository.save(novaTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> listarTasks(String status, String priority, Long idProject) {
        List<Task> tasks = taskRepository.findByStatusOrPriorityOrIdProject(status, priority, idProject);
        return tasks.stream()
                .map(TaskResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> listarTasks() {
        List<Task> listarTodas = taskRepository.findAll();
        return listarTodas.stream()
                .map(TaskResponseDTO::new)
                .collect(Collectors.toList());
    }

}
