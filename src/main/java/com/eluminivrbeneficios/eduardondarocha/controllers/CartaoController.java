package com.eluminivrbeneficios.eduardondarocha.controllers;

import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoRequestCriar;
import com.eluminivrbeneficios.eduardondarocha.dto.controller.CartaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

    @PostMapping
    public ResponseEntity<CartaoResponse> criar (@RequestBody CartaoRequestCriar cartaoRequestCriar){
        return null;
    }
}
