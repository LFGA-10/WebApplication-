package com.downloader.skillswitch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_requests")
public class SkillRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User teacher;

    @ManyToOne
    private Skill skill;

    private String status;

    public SkillRequest() {}

    public SkillRequest(User requester, User teacher, Skill skill, String status) {
        this.requester = requester;
        this.teacher = teacher;
        this.skill = skill;
        this.status = status;
    }

    public int getId() { return id; }
    public User getRequester() { return requester; }
    public User getTeacher() { return teacher; }
    public Skill getSkill() { return skill; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}