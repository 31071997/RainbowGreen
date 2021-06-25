package com.example.rainbowgreen;

import android.widget.EditText;
import android.widget.ImageView;

public class Model {
    public Model() { }
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) { Url = url; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String Url;

    public Model(String url, String text) {
        Url = url;
        this.text = text;
    }

    String text;
}
