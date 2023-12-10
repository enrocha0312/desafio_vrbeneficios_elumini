package com.eluminivrbeneficios.eduardondarocha.controllers;

import com.eluminivrbeneficios.eduardondarocha.dto.controller.TransacaoRequest;
import com.eluminivrbeneficios.eduardondarocha.dto.service.Transacao;
import com.eluminivrbeneficios.eduardondarocha.services.CartaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private CartaoService cartaoService;
    @PostMapping
    public ResponseEntity<String> fazerTransacao(@RequestBody TransacaoRequest transacaoRequest){
        Transacao transacao = new ModelMapper().map(transacaoRequest, Transacao.class);
        try{
            String resposta = cartaoService.realizarTransacao(transacao);
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
