package com.example.pasteBox.service;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.request.PublicStatus;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteUrlResponse;
import com.example.pasteBox.repository.PasteBoxEntity;
import com.example.pasteBox.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class PasteBoxServiceImpl implements PasteBoxService {

    private String host = "https://abc.ru";
    private int publicListSize = 10;

    private final PasteBoxRepository repository;
    private AtomicInteger idGenarator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBox() {
        List<PasteBoxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(pasteBoxEntity -> new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteBoxEntity);

        return new PasteUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return idGenarator.getAndIncrement();
    }
}
