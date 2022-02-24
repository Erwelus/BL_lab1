package com.example.bl_lab1.controllers;

import com.example.bl_lab1.dto.SectionDto;
import com.example.bl_lab1.service.ArticleService;
import com.example.bl_lab1.service.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("section")
public class SectionController {
    private final SectionService service;
    private final ArticleService articleService;

    private SectionController(SectionService service, ArticleService articleService){
        this.service=service;
        this.articleService = articleService;
    }

    @GetMapping("code")
    public ResponseEntity getCode(@RequestBody SectionDto data){
        Integer articleId = articleService.getIdByName(data.getArticleName());
        String response = service.getSectionCodeByArticleIdAndIndex(articleId, data.getSectionIndex());
        return ResponseEntity.ok(response);
    }

}
