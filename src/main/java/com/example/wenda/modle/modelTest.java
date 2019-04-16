package com.example.wenda.modle;

public class modelTest {

    public String name;
    public int age;
    public String string;

    public modelTest(String name, int age, String string) {
        this.name = name;
        this.age = age;
        this.string = string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
