package com.example.bl_lab1.repositories;

import com.example.bl_lab1.model.SectionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SectionRepo extends CrudRepository<SectionEntity, Integer> {
    @Transactional
    @Query(value = "select * from SECTION where article_id = ?1 and sectionorder = ?2", nativeQuery = true)
    SectionEntity findByArticleIdAndSectionOrder(Integer articleId, Integer order);
    @Transactional
    @Modifying
    @Query(value = "update section set newesttext = ?1 where id = ?2", nativeQuery = true)
    void updateText(String text, Integer id);
}
