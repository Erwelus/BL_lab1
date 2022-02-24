package com.example.bl_lab1.service.impl;

import com.example.bl_lab1.repositories.ArticleRepo;
import com.example.bl_lab1.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepo repo;

    public ArticleServiceImpl(ArticleRepo repo){
        this.repo = repo;
    }

    @Override
    public Integer getIdByName(String name) {
        return repo.findFirstByArticlename(name).getId();
    }
}
