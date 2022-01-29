package com.example.pasteBox.controller;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.request.PublicStatus;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteUrlResponse;
import com.example.pasteBox.repository.PasteBoxRepository;
import com.example.pasteBox.service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;


@RestController
@RequiredArgsConstructor
public class PasteBoxController {
    private final PasteBoxService pasteBoxService;


    @GetMapping("/")
    public Collection<PasteBoxResponse> getPasteList(){
        return pasteBoxService.getFirstPublicPasteBox();
    }

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash) {
        return pasteBoxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteUrlResponse add(@RequestBody PasteBoxRequest request) {
        return pasteBoxService.create(request);
    }

}
