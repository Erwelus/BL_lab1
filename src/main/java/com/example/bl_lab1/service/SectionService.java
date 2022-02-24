package com.example.bl_lab1.service;

import com.example.bl_lab1.model.SectionEntity;

public interface SectionService {
    Integer getIdByArticleIdAndIndex(Integer articleId, Integer index);
    String getSectionCodeByArticleIdAndIndex(Integer articleId, Integer index);
    SectionEntity getSectionByArticleIdAndIndex(Integer articleId, Integer index);
}
