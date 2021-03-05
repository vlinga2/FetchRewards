package com.example.fetchrewards;

public class ListItem {
    private String name;
    private String listId;
    private String id;

    ListItem(String name, String listId, String id){
        this.name = name;
        this.listId = listId;
        this.id = id;
    }

    public String getName() {
        return name.substring(1,name.length()-1);
    }

    public String getListId() {
        return listId;
    }

    public String getId() {
        return id;
    }
}
