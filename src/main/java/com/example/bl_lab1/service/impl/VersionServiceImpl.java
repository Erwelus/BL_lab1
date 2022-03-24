package com.example.bl_lab1.service.impl;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.example.bl_lab1.model.SectionEntity;
import com.example.bl_lab1.model.VersionEntity;
import com.example.bl_lab1.repositories.SectionRepo;
import com.example.bl_lab1.repositories.VersionRepo;
import com.example.bl_lab1.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {
    private final UserTransactionImp utx;
    private final AtomikosDataSourceBean dataSource;

    private final VersionRepo repo;
    private final SectionRepo sectionRepo;

    //todo change method signature

    @Override
    public void saveChangesByAuthorizedUser(String newText, String username, SectionEntity section) throws Exception {
        boolean rollback = false;
        try {
            utx.begin();
            Connection connection = dataSource.getConnection();
            VersionEntity entity = new VersionEntity();
            entity.setSectionId(section.getId());
            entity.setPersonedited(username);
            entity.setSectiontext(newText);
            entity.setStatus("Ожидает проверки");
            repo.save(entity);

            sectionRepo.updateText(newText, section.getId());

            connection.close();
        } catch (Exception e) {
            rollback = true;
            e.printStackTrace();
        } finally {
            if (!rollback) utx.commit();
            else utx.rollback();
        }
    }

    @Override
    public void saveChangesByUnauthorizedUser(String newText, String ip, SectionEntity section) throws Exception {
        boolean rollback = false;
        try {
            utx.begin();
            Connection connection = dataSource.getConnection();
            VersionEntity entity = new VersionEntity();
            entity.setSectionId(section.getId());
            entity.setIpedited(ip);
            entity.setSectiontext(newText);
            entity.setStatus("Ожидает проверки");
            repo.save(entity);

            sectionRepo.updateText(newText, section.getId());

            connection.close();
        } catch (Exception e) {
            rollback = true;
            e.printStackTrace();
        } finally {
            if (!rollback) utx.commit();
            else utx.rollback();
        }
    }

    @Override
    public List<VersionEntity> getListOfWaitingUpdates() {
        return repo.findAllByStatus("Ожидает проверки");
    }

    @Override
    public String getTextOfLastUpdateBySection(SectionEntity section) {
        List<VersionEntity> versionEntities = repo.findAllBySectionId(section.getId());
        return Collections.max(versionEntities).getSectiontext();
    }

    @Override
    public String getTextOfLastApprovedVersion(SectionEntity section) {
        List<VersionEntity> versionEntities = repo.findAllBySectionIdAndStatus(section.getId(), "Принято");
        String text = Collections.max(versionEntities).getSectiontext();
        sectionRepo.updateText(text, section.getId());
        return text;
    }

    @Override
    public void approve(Integer id) {
        VersionEntity entity = repo.findById(id).get();
        entity.setStatus("Принято");
        repo.save(entity);
    }

    @Override
    public void decline(Integer id) throws Exception{
        boolean rollback = false;
        try {
            utx.begin();
            Connection connection = dataSource.getConnection();
            VersionEntity entity = repo.findById(id).get();
            entity.setStatus("Отклонено");
            repo.save(entity);

            SectionEntity sectionEntity = repo.findById(id).get().getSection();
            String text = getTextOfLastApprovedVersion(sectionEntity);
            sectionRepo.updateText(text, sectionEntity.getId());

            connection.close();
        } catch (Exception e) {
            rollback = true;
            e.printStackTrace();
        } finally {
            if (!rollback) utx.commit();
            else utx.rollback();
        }
    }
}
