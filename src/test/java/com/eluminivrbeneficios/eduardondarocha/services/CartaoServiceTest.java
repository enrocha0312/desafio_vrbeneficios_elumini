package com.eluminivrbeneficios.eduardondarocha.services;

import com.eluminivrbeneficios.eduardondarocha.dto.service.Transacao;
import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoJaExistenteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoNaoEncontradoException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.SaldoInsuficienteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.SenhaInvalidaException;
import com.eluminivrbeneficios.eduardondarocha.repositories.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CartaoServiceTest {
    private static String NUM_CARTAO = "cartao1234";
    private static String SENHA = "senha";
    @InjectMocks
    private CartaoService cartaoService;
    @Mock
    private CartaoRepository cartaoRepository;
    private Cartao cartao;
    private Optional<Cartao> cartaoOptional;
    private Transacao transacao;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        cartao = Cartao.builder()
                .saldo(500.0)
                .numeroCartao(NUM_CARTAO)
                .senha(SENHA)
                .build();
        transacao = Transacao.builder()
                .valor(200.0)
                .numeroCartao(NUM_CARTAO)
                .senha(SENHA)
                .build();
        cartaoOptional = Optional.of(cartao);
    }

    @Test
    void postFuncionaCorretamente(){
        when(cartaoRepository.save(any())).thenReturn(cartao);
        when(cartaoRepository.findById(anyString())).thenReturn(Optional.empty());
        Cartao response = cartaoService.criar(cartao);
        assertNotNull(response);
        assertEquals(Cartao.class, response.getClass());
        assertEquals(NUM_CARTAO, response.getNumeroCartao());
        assertEquals(SENHA, response.getSenha());
        assertEquals(500.0, response.getSaldo());
    }

    @Test
    void postNaoCriaCartaoPorqueJaExiste(){
        when(cartaoRepository.findById(anyString())).thenReturn(cartaoOptional);
        try{
            Cartao response = cartaoService.criar(cartao);
        }catch (CartaoJaExistenteException e){
            assertEquals(e.getClass(), CartaoJaExistenteException.class);
            assertEquals(e.getMessage(), "Cartão com número " + NUM_CARTAO + " já foi criado");
        }
    }

    @Test
    void retornaSaldoComSucesso(){
        when(cartaoRepository.findById(anyString())).thenReturn(cartaoOptional);
        cartaoOptional.get().setSaldo(100.0);
        Double response = cartaoService.retornarSaldoDeCartaoPorNumero(NUM_CARTAO);
        assertEquals(response.getClass(), Double.class);
        assertEquals(response, 100.0);
    }

    @Test
    void retornaSaldoNaoEncontraCartao(){
        when(cartaoRepository.findById(anyString())).thenReturn(Optional.empty());
        try{
            cartaoOptional.get().setSaldo(100.0);
            Double response = cartaoService.retornarSaldoDeCartaoPorNumero(NUM_CARTAO);
        }catch (Exception e){
            assertEquals(e.getClass(), CartaoNaoEncontradoException.class);
            assertEquals(e.getMessage(), "Cartão com número " + NUM_CARTAO + " não encontrado");
        }
    }

    @Test
    void transacaoComSucesso(){
        when(cartaoRepository.findById(anyString())).thenReturn(cartaoOptional);
        when(cartaoRepository.save(any())).thenReturn(cartaoOptional.get());
        String response = cartaoService.realizarTransacao(transacao);
        assertEquals(response, "Ok");
        assertEquals(response.getClass(), String.class);
        assertEquals(cartaoOptional.get().getSaldo(), 300.0);
    }

    @Test
    void transacaoNaoEncontraCartao(){
        when(cartaoRepository.findById(anyString())).thenReturn(Optional.empty());
        try{
            cartaoOptional.get().setSaldo(200.0);
            String response = cartaoService.realizarTransacao(transacao);
        }catch (Exception e){
            assertEquals(e.getClass(), CartaoNaoEncontradoException.class);
            assertEquals(e.getMessage(), "Cartão com número " + NUM_CARTAO + " não encontrado");
        }
    }

    @Test
    void transacaoComSenhaErrada(){
        when(cartaoRepository.findById(anyString())).thenReturn(cartaoOptional);
        cartaoOptional.get().setSenha("senhanova");
        try{
            cartaoOptional.get().setSaldo(200.0);
            String response = cartaoService.realizarTransacao(transacao);
        }catch (Exception e){
            assertEquals(e.getClass(), SenhaInvalidaException.class);
            assertEquals(e.getMessage(), "Senha inválida!");
        }
    }

    @Test
    void transacaoComSaldoInsuficiente(){
        when(cartaoRepository.findById(anyString())).thenReturn(cartaoOptional);
        transacao.setValor(1000.0);
        try{
            String response = cartaoService.realizarTransacao(transacao);
        }catch (Exception e){
            assertEquals(e.getClass(), SaldoInsuficienteException.class);
            assertEquals(e.getMessage(), "Saldo insuficiente!");
        }
    }
}
