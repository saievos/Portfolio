package com.techelevator.model;

public class ListDTO {
    private String listName;
    private String updateName;

    public ListDTO() {

    }

    public ListDTO(String listName) {
        this.listName = listName;
    }

    public ListDTO(String listName, String updateName) {
        this.listName = listName;
        this.updateName = updateName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
