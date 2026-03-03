/**
 * Give a request to a skilll that you want to attain or you need
 */

package com.downloader.skillswitch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    /**
     * User id listed here
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the user with the id provided
     */

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The skill that the user has
     */

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private String status = "Pending";

    public Request() {}

    public Request(User user, Skill skill) {
        this.user = user;
        this.skill = skill;
    }

    /**
     * Setters and gettters of the request class
     * @return the id, User, skill and status of the person requesting the skill
     */
    // getters and setters
    public int getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}