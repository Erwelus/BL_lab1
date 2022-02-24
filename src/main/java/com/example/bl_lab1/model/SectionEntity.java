package com.example.bl_lab1.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "section")
public class SectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity articleEntity;

    @Column(name = "sectionorder")
    private Integer sectionorder;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "newesttext")
    private String newesttext;

    @Column(name = "latestdate")
    private Instant latestdate;

    public Instant getLatestdate() {
        return latestdate;
    }

    public void setLatestdate(Instant latestdate) {
        this.latestdate = latestdate;
    }

    public String getNewesttext() {
        return newesttext;
    }

    public void setNewesttext(String newesttext) {
        this.newesttext = newesttext;
    }

    public Integer getSectionorder() {
        return sectionorder;
    }

    public void setSectionorder(Integer sectionorder) {
        this.sectionorder = sectionorder;
    }

    public ArticleEntity getArticle() {
        return articleEntity;
    }

    public void setArticle(ArticleEntity articleEntity) {
        this.articleEntity = articleEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}