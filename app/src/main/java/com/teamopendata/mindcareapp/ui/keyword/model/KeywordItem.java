package com.teamopendata.mindcareapp.ui.keyword.model;

import com.teamopendata.mindcareapp.common.object.ItemType;
import com.teamopendata.mindcareapp.common.object.Keyword;

import java.util.ArrayList;
import java.util.List;

public class KeywordItem {
    private String header;
    private List<Keyword> keywords;
    private ItemType type;

    public KeywordItem(ItemType type) {
        this.type = type;
    }

    public KeywordItem(String header, List<String> keywords) {
        this.header = header;
        this.keywords = new ArrayList<>();
        for (String keyword : keywords) this.keywords.add(Keyword.findBy(keyword));
        type = ItemType.TYPE_ITEM;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
