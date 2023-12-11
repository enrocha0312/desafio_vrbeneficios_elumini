package com.eluminivrbeneficios.eduardondarocha.controllers;

import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoRequest;
import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoResponse;
import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoUpdateSaldo;
import com.eluminivrbeneficios.eduardondarocha.entities.Cartao;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoJaExistenteException;
import com.eluminivrbeneficios.eduardondarocha.exceptions.service.CartaoNaoEncontradoException;
import com.eluminivrbeneficios.eduardondarocha.services.CartaoService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {
    @Autowired
    private CartaoService cartaoService;
    @PostMapping
    public ResponseEntity<CartaoResponse> criar (@RequestBody CartaoRequest cartaoRequest){
        Cartao cartao = new ModelMapper().map(cartaoRequest, Cartao.class);
        try{
            cartao = cartaoService.criar(cartao);
            CartaoResponse cartaoResponse = new ModelMapper().map(cartao, CartaoResponse.class);
            return new ResponseEntity<>(cartaoResponse, HttpStatus.CREATED);
        }catch (CartaoJaExistenteException e){
            CartaoResponse cartaoResponse = new ModelMapper().map(cartaoRequest, CartaoResponse.class);
            return new ResponseEntity<>(cartaoResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PutMapping("/{numCartao}")
    public ResponseEntity<CartaoUpdateSaldo> atualizar (@RequestBody CartaoUpdateSaldo cartaoUpdateSaldo, @PathVariable String numCartao){
        Cartao cartao = new ModelMapper().map(cartaoUpdateSaldo, Cartao.class);
        try {
            cartao = cartaoService.atualizarCartao(numCartao, cartao);
            CartaoUpdateSaldo response = new ModelMapper().map(cartao, CartaoUpdateSaldo.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(CartaoUpdateSaldo.builder().numeroCartao(cartaoUpdateSaldo.getNumeroCartao()).build(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{numCartao}")
    public ResponseEntity<?> retornaSaldo(@PathVariable String numCartao){
        try{
            Double saldo = cartaoService.retornarSaldoDeCartaoPorNumero(numCartao);
            return new ResponseEntity<>(saldo, HttpStatus.OK);
        }catch (CartaoNaoEncontradoException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
