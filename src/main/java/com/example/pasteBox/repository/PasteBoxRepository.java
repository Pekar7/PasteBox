package com.example.pasteBox.repository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteBoxRepository {
    PasteBoxEntity getByHash(String hash);
    List<PasteBoxEntity> getListOfPublicAndAlive(int amount);
    void add(PasteBoxEntity pasteBoxEntity);
}
