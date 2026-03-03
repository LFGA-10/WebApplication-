/**
 * Find two people or more people with a certain skill they both want and helps with the exchange
 */

package com.downloader.skillswitch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exchanges")
public class Exchange {

    /**
     * Person's id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /**
     * The teacher
     */

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    /**
     * The learner
     */

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private User learner;

    /**
     * The skill to be taught or learned
     */

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public Exchange() {}

    public Exchange(User teacher, User learner, Skill skill) {
        this.teacher = teacher;
        this.learner = learner;
        this.skill = skill;
    }

    /**
     * getters and setters for the exchange class
     * @return
     */

    public int getId() { return id; }
    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }
    public User getLearner() { return learner; }
    public void setLearner(User learner) { this.learner = learner; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
}