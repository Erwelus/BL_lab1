package com.example.bl_lab1.service;

import com.example.bl_lab1.dto.VersionDto;
import com.example.bl_lab1.model.SectionEntity;
import com.example.bl_lab1.model.VersionEntity;

import java.util.List;

public interface VersionService {
    void saveChangesByAuthorizedUser(String newText, String username, SectionEntity section);
    void saveChangesByUnauthorizedUser(String newText, String ip, SectionEntity section);
    List<VersionEntity> getListOfWaitingUpdates();
    String getTextOfLastUpdateBySection(SectionEntity section);
    String getTextOfLastApprovedVersion(SectionEntity section);
    void approve(Integer id);
    void decline(Integer id);
}
