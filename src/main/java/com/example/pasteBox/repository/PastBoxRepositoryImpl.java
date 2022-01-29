package com.example.pasteBox.repository;

import com.example.pasteBox.exception.NotFoundEntityException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PastBoxRepositoryImpl implements PasteBoxRepository{

    private final Map<String, PasteBoxEntity> vault = new ConcurrentHashMap<>();
    @Override
    public PasteBoxEntity getByHash(String hash) {
        PasteBoxEntity pasteBox = vault.get(hash);
        if (pasteBox == null) {
            throw new NotFoundEntityException("PasteBox not found, hash = " + hash);
        }
        return vault.get(hash);
    }

    @Override
    public List<PasteBoxEntity> getListOfPublicAndAlive(int amount) {
        LocalDateTime now = LocalDateTime.now();
        return vault.values().stream()
                .filter(PasteBoxEntity::isPublic)
                .filter(PasteBoxEntity -> PasteBoxEntity.getLifetime().isAfter(now))
                .sorted(Comparator.comparing(PasteBoxEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PasteBoxEntity pasteBoxEntity) {
        vault.put(pasteBoxEntity.getHash(), pasteBoxEntity);
    }
}
