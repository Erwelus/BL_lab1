package com.example.bl_lab1.controllers;

import com.example.bl_lab1.dto.IdDto;
import com.example.bl_lab1.dto.SectionDto;
import com.example.bl_lab1.dto.VersionDto;
import com.example.bl_lab1.model.SectionEntity;
import com.example.bl_lab1.service.ArticleService;
import com.example.bl_lab1.service.SectionService;
import com.example.bl_lab1.service.UserService;
import com.example.bl_lab1.service.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("version")
public class VersionController {
    private final VersionService service;
    private final SectionService sectionService;
    private final ArticleService articleService;
    private final UserService userService;

    public VersionController(VersionService service, SectionService sectionService, ArticleService articleService, UserService userService) {
        this.service = service;
        this.sectionService = sectionService;
        this.articleService = articleService;
        this.userService = userService;
    }

    @PostMapping("save")
    public void save(@RequestBody VersionDto data) {
        Integer articleId = articleService.getIdByName(data.getArticleName());
        SectionEntity section = sectionService.getSectionByArticleIdAndIndex(articleId, data.getSectionIndex());
        String curUsername = userService.getCurrentUserName();
        if (curUsername==null){
            service.saveChangesByUnauthorizedUser(data.getNewText(), data.getIp(), section);
        } else service.saveChangesByAuthorizedUser(data.getNewText(), curUsername, section);
    }

    @GetMapping("all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(service.getListOfWaitingUpdates());
    }

    @GetMapping("last")
    public ResponseEntity getLast(@RequestBody SectionDto data){
        Integer articleId = articleService.getIdByName(data.getArticleName());
        SectionEntity section = sectionService.getSectionByArticleIdAndIndex(articleId, data.getSectionIndex());
        return ResponseEntity.ok(service.getTextOfLastUpdateBySection(section));
    }

    @GetMapping("previous")
    public ResponseEntity getPrevious(@RequestBody SectionDto data){
        Integer articleId = articleService.getIdByName(data.getArticleName());
        SectionEntity section = sectionService.getSectionByArticleIdAndIndex(articleId, data.getSectionIndex());
        return ResponseEntity.ok(service.getTextOfLastApprovedVersion(section));
    }

    @PostMapping("approve")
    public void approve(@RequestBody IdDto data){
        service.approve(data.getId());
    }

    @PostMapping("decline")
    public void decline(@RequestBody IdDto data){
        service.decline(data.getId());
    }
}
