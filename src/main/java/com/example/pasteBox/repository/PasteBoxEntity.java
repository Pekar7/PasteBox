package com.example.pasteBox.repository;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PasteBoxEntity {
    private int id;
    private String data;
    private String hash;
    private LocalDateTime lifetime;
    private boolean isPublic;
}
