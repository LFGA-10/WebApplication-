package com.downloader.skillswitch.model;

public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;

    public Person() {}

    public Person(Integer id, String firstName, String lastName, Gender gender, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge(){
        return age;
    }

}
