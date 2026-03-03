/**
 * Find and save the skill that a user has input
 */


package com.downloader.skillswitch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String skillName;

    private String category;

    public Skill() {}

    public Skill(String skillName, String category) {
        this.skillName = skillName;
        this.category = category;
    }

    // getters and setters
    public int getId() { return id; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}