package com.desafio.gerenciamento.service;

import com.desafio.gerenciamento.handler.ProjectExists;
import com.desafio.gerenciamento.model.Project;
import com.desafio.gerenciamento.repository.ProjectRepository;
import com.desafio.gerenciamento.request.ProjectRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks // * criar uma instancia da nossa classe de service, injetando os mocks, como o repository
    private ProjectService projectService;

    @Captor
    private ArgumentCaptor<Project> argumentCaptor;

    @Nested // * junit identificar que é uma subclasse
    class criarProjeto{

        @Test
        @DisplayName("Deve criar um projeto com sucesso")
        void deveCriarUmProjeto() {
            //dado
            ProjectRequestDTO dto = new ProjectRequestDTO();
            dto.setName("BBBB");
            dto.setDescription("Descrição do projeto");
            dto.setStartDate(LocalDate.now());
            dto.setEndDate(LocalDate.now().plusMonths(3));

            //quando
            doReturn(null).when(projectRepository).findByName(dto.getName());
            when(projectRepository.save(any(Project.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            //service
            Project output = projectService.criarProjeto(dto);

            //entao
            verify(projectRepository,times(1)).save(argumentCaptor.capture());
            Project projetoCapturado = argumentCaptor.getValue();

            assertNotNull(output,"O output não pode ser nulo");
            assertEquals(projetoCapturado.getId(),output.getId());
            assertEquals(projetoCapturado.getName(),output.getName());
            assertEquals(projetoCapturado.getStartDate(),output.getStartDate());
            assertEquals(projetoCapturado.getEndDate(),output.getEndDate());
        }

        @Test
        @DisplayName("Deve lançar uma exceção se o projeto já existir")
        void deveLancarExcecaoSeNomeForEncontrado() {
            //dado
            ProjectRequestDTO dto = new ProjectRequestDTO();
            dto.setName("BBBB");
            dto.setDescription("Descrição do projeto");
            dto.setStartDate(LocalDate.now());
            dto.setEndDate(LocalDate.now().plusMonths(3));
            Project projetoExistente = new Project("BBBB","Descrição do projeto",LocalDate.now(),LocalDate.now().plusMonths(3));

            //quando
            doReturn(projetoExistente).when(projectRepository).findByName(dto.getName());

            //entao
            assertThrows(ProjectExists.class,() -> projectService.criarProjeto(dto));
        }

    }
}