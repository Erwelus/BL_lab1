package com.example.bl_lab1.service.impl;

import com.example.bl_lab1.model.SectionEntity;
import com.example.bl_lab1.repositories.SectionRepo;
import com.example.bl_lab1.service.SectionService;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService {
    private final SectionRepo repo;

    public SectionServiceImpl(SectionRepo repo) {
        this.repo = repo;
    }

    @Override
    public String getSectionCodeByArticleIdAndIndex(Integer articleId, Integer index) {
        return getSectionByArticleIdAndIndex(articleId, index).getNewesttext();
    }

    @Override
    public Integer getIdByArticleIdAndIndex(Integer articleId, Integer index){
        return getSectionByArticleIdAndIndex(articleId, index).getId();
    }

    @Override
    public SectionEntity getSectionByArticleIdAndIndex(Integer articleId, Integer index){
        return repo.findByArticleIdAndSectionOrder(articleId, index);
    }
}
