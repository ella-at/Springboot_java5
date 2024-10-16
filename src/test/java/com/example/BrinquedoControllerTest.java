package com.example;

import com.example.controllers.BrinquedoController;
import com.example.models.Brinquedo;
import com.example.services.BrinquedoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BrinquedoControllerTest {

    @Mock
    private BrinquedoService brinquedoService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private BrinquedoController brinquedoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListaBrinquedos() throws Exception {
        // Mocka um brinquedo para ser retornado pelo serviço
        Brinquedo brinquedoMock = new Brinquedo();
        brinquedoMock.setId(1L);
        brinquedoMock.setNome("Carrinho");

        // Simula o comportamento do serviço buscarPorId
        when(brinquedoService.buscarPorId(1L)).thenReturn(brinquedoMock);

        // Chama o meodo do controlador passando o ID e o Model
        String view = brinquedoController.listarBrinquedos(1L, model);

        // Verifica se a view retornada é correta
        assertEquals("brinquedos/detail", view);

        // Verifica se o brinquedo foi adicionado ao modelo
        verify(model, times(1)).addAttribute("brinquedo", brinquedoMock);

        // Verifica se o serviço foi chamado corretamente
        verify(brinquedoService, times(1)).buscarPorId(1L);
    }


    @Test
    public void testExibirFormularioCriacao() {
        // Testa se o formulário de criação é exibido corretamente
        String view = brinquedoController.showCreateForm(model);
        assertEquals("brinquedos/create", view);
    }

    @Test
    public void testSalvarBrinquedoComSucesso() {
        // Testa se o brinquedo é salvo corretamente e redireciona para a página correta
        Brinquedo brinquedo = new Brinquedo();
        brinquedo.setNome("Boneca");
        brinquedo.setDescricao("Boneca de pano");
        brinquedo.setPreco(50.0);
        brinquedo.setQuantidade(10);
        brinquedo.setEstado("novo");

        when(brinquedoService.save(brinquedo)).thenReturn(brinquedo);

        String view = brinquedoController.createBrinquedo(brinquedo, redirectAttributes);
        assertEquals("redirect:/brinquedos/lista", view);
        verify(brinquedoService, times(1)).save(brinquedo);
    }

    @Test
    public void testExibirFormularioEdicao() throws Exception {
        // Testa se o formulário de edição é exibido corretamente
        Brinquedo brinquedo = new Brinquedo();
        brinquedo.setId(1L);

        when(brinquedoService.buscarPorId(1L)).thenReturn(brinquedo);

        String view = brinquedoController.showEditForm(1L, model);
        assertEquals("brinquedos/editar", view);
        verify(brinquedoService, times(1)).buscarPorId(1L);
    }

    @Test
    public void testAtualizarBrinquedo() throws Exception {
        // Testa se a atualização de brinquedo ocorre corretamente
        Brinquedo brinquedo = new Brinquedo();
        brinquedo.setId(1L);
        brinquedo.setNome("Carrinho");

        when(brinquedoService.save(brinquedo)).thenReturn(brinquedo);

        String view = brinquedoController.updateBrinquedo(1L, brinquedo, redirectAttributes);
        assertEquals("redirect:/brinquedos/lista", view);
        verify(brinquedoService, times(1)).save(brinquedo);
    }

    @Test
    public void testDeletarBrinquedo() throws Exception {
        // Testa se o brinquedo é deletado corretamente
        String view = brinquedoController.deleteBrinquedo(1L, redirectAttributes);
        assertEquals("redirect:/brinquedos/lista", view);
        verify(brinquedoService, times(1)).deletarPorId(1L);
    }
}
