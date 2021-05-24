package com.congnghephanmem.filmhay.Model;

public class Report {
    private String id;
    private String nameReport;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameReport() {
        return nameReport;
    }

    public void setNameReport(String nameReport) {
        this.nameReport = nameReport;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Report() {
    }

    public Report(String id, String nameReport, String comment) {
        this.id = id;
        this.nameReport = nameReport;
        this.comment = comment;
    }
}
