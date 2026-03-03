package com.downloader.skillswitch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_skills")
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private String level;

    public UserSkill() {}

    public UserSkill(User user, Skill skill, String level) {
        this.user = user;
        this.skill = skill;
        this.level = level;
    }

    // getters and setters
    public int getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}