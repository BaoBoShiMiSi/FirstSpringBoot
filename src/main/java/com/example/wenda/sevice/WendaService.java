package com.example.wenda.sevice;

import org.springframework.stereotype.Service;

@Service
public class WendaService {
    public String getMessage(int userId) {

        return "message:" + userId;
    }
}
