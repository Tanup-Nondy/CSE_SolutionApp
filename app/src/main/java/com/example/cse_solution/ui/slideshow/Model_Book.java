package com.example.cse_solution.ui.slideshow;

public class Model_Book {
    String sub,name,image,pdf,id;

    public Model_Book(String id,String sub, String name, String image, String pdf) {
        this.id=id;
        this.sub = sub;
        this.name = name;
        this.image = image;
        this.pdf = pdf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
