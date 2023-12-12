package com.eluminivrbeneficios.eduardondarocha.controllers;

import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoRequest;
import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoResponse;
import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoJaExistenteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoNaoEncontradoException;
import com.eluminivrbeneficios.eduardondarocha.services.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CartaoControllerTest {
    @Mock
    private CartaoService cartaoService;
    @InjectMocks
    private CartaoController cartaoController;
    @Mock
    private ModelMapper mapper;
    private static String NUM_CARTAO = "cartao1234";
    private static String SENHA = "senha";
    private CartaoResponse cartaoResponse;
    private CartaoRequest cartaoRequest;
    private Cartao cartao;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        cartao =  Cartao.builder()
                .senha(SENHA)
                .numeroCartao(NUM_CARTAO)
                .saldo(500.0)
                .build();
        cartaoRequest = CartaoRequest.builder()
                .numeroCartao(NUM_CARTAO)
                .senha(SENHA)
                .build();
        cartaoResponse = CartaoResponse.builder()
                .numeroCartao(NUM_CARTAO)
                .senha(SENHA)
                .build();
    }

    @Test
    void criaCartaoComSucesso(){
        when(cartaoService.criar(any())).thenReturn(cartao);
        when(mapper.map(any(), any())).thenReturn(cartao).thenReturn(cartaoResponse);
        ResponseEntity<CartaoResponse> response = cartaoController.criar(cartaoRequest);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getNumeroCartao(), NUM_CARTAO);
        assertEquals(response.getBody().getSenha(), SENHA);
    }

    @Test
    void naoCriaCartaoPorqueJaExistia(){
        when(cartaoService.criar(any())).thenThrow(CartaoJaExistenteException.class);
        when(mapper.map(any(), any())).thenReturn(cartao).thenReturn(cartaoResponse);
        ResponseEntity<CartaoResponse> response = cartaoController.criar(cartaoRequest);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(response.getBody().getNumeroCartao(), NUM_CARTAO);
        assertEquals(response.getBody().getSenha(), SENHA);
    }
    @Test
    void retornaSaldoComSucesso(){
        when(cartaoService.retornarSaldoDeCartaoPorNumero(anyString())).thenReturn(100.0);
        ResponseEntity<Double> response = (ResponseEntity<Double>) cartaoController.retornaSaldo(NUM_CARTAO);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response);
        assertEquals(response.getBody().getClass(), Double.class);
    }

    @Test
    void retornaSaldoCartaoInexistente(){
        when(cartaoService.retornarSaldoDeCartaoPorNumero(anyString())).thenThrow(CartaoNaoEncontradoException.class);
        ResponseEntity<?> response = cartaoController.retornaSaldo(NUM_CARTAO);
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
