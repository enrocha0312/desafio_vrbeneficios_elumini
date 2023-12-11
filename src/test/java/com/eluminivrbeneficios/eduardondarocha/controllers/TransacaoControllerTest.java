package com.eluminivrbeneficios.eduardondarocha.controllers;

import com.eluminivrbeneficios.eduardondarocha.dto.controller.TransacaoRequest;
import com.eluminivrbeneficios.eduardondarocha.dto.service.Transacao;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoNaoEncontradoException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.SaldoInsuficienteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.SenhaInvalidaException;
import com.eluminivrbeneficios.eduardondarocha.services.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransacaoControllerTest {
    @Mock
    private CartaoService cartaoService;
    @InjectMocks
    private TransacaoController transacaoController;
    private TransacaoRequest transacaoRequest;
    private Transacao transacao;
    private static String NUM_CARTAO = "cartao1234";
    private static String SENHA = "senha";
    private static Double VALOR = 100.0;
    @Mock
    private ModelMapper mapper;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        transacaoRequest = TransacaoRequest.builder()
                .senha(SENHA)
                .numeroCartao(NUM_CARTAO)
                .valor(VALOR)
                .build();
        transacao = Transacao.builder()
                .valor(VALOR)
                .senha(SENHA)
                .numeroCartao(NUM_CARTAO)
                .build();
    }

    @Test
    void transacaoComSucesso(){
        when(mapper.map(any(), any())).thenReturn(transacao);
        when(cartaoService.realizarTransacao(any())).thenReturn("Ok");
        ResponseEntity<String> response = transacaoController.fazerTransacao(transacaoRequest);
        assertNotNull(response);
        assertEquals(response.getBody().getClass(), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void transacaoParaCartaoInexistente(){
        when(mapper.map(any(), any())).thenReturn(transacao);
        when(cartaoService.realizarTransacao(any())).thenThrow(CartaoNaoEncontradoException.class);
        ResponseEntity<String> response = transacaoController.fazerTransacao(transacaoRequest);
        assertNotNull(response);
        assertEquals(response.getBody().getClass(), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(response.getBody(), "SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE");
    }

    @Test
    void transacaoSemSaldoSuficiente(){
        when(mapper.map(any(), any())).thenReturn(transacao);
        when(cartaoService.realizarTransacao(any())).thenThrow(SaldoInsuficienteException.class);
        ResponseEntity<String> response = transacaoController.fazerTransacao(transacaoRequest);
        assertNotNull(response);
        assertEquals(response.getBody().getClass(), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(response.getBody(), "SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE");
    }
    @Test
    void transacaoSenhaInvalida(){
        when(mapper.map(any(), any())).thenReturn(transacao);
        when(cartaoService.realizarTransacao(any())).thenThrow(SenhaInvalidaException.class);
        ResponseEntity<String> response = transacaoController.fazerTransacao(transacaoRequest);
        assertNotNull(response);
        assertEquals(response.getBody().getClass(), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(response.getBody(), "SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE");
    }
}
