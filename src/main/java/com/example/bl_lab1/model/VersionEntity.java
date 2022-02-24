package com.example.bl_lab1.model;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "version")
public class VersionEntity implements Comparable<VersionEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "section_id", nullable = false, insertable = false, updatable = false)
    private SectionEntity sectionEntity;

    @Column(name = "section_id", nullable = false)
    private Integer sectionId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "sectiontext")
    private String sectiontext;

    @Column(name = "dateedited", nullable = false, updatable = false, insertable = false)
    private Instant dateedited;

    @Lob
    @Type(type = "org.hibernate.type.StringType")
    @Column(name = "personedited")
    private String personedited;

    @Lob
    @Type(type = "org.hibernate.type.StringType")
    @Column(name = "ipedited")
    private String ipedited;

    @Column(name = "status")
    private String status;

    public String getIpedited() {
        return ipedited;
    }

    public void setIpedited(String ipedited) {
        this.ipedited = ipedited;
    }

    public String getPersonedited() {
        return personedited;
    }

    public void setPersonedited(String personedited) {
        this.personedited = personedited;
    }

    public Instant getDateedited() {
        return dateedited;
    }

    public void setDateedited(Instant dateedited) {
        this.dateedited = dateedited;
    }

    public String getSectiontext() {
        return sectiontext;
    }

    public void setSectiontext(String sectiontext) {
        this.sectiontext = sectiontext;
    }

    public SectionEntity getSection() {
        return sectionEntity;
    }

    public void setSection(SectionEntity sectionEntity) {
        this.sectionEntity = sectionEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public int compareTo(VersionEntity o) {
        return this.dateedited.compareTo(o.getDateedited());
    }
}