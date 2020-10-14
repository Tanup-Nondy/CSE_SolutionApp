package com.example.cse_solution.ui.gallery;

public class Model_S {
    String session,semester;

    public Model_S(String session, String semester) {

        this.session = session;
        this.semester = semester;
    }


    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
