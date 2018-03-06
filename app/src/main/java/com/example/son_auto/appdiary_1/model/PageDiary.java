package com.example.son_auto.appdiary_1.model;

/**
 * Created by Son-Auto on 3/5/2018.
 */

public class PageDiary {
    private String content, emotion, background;
    private int id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PageDiary() {
    }

    public PageDiary(String content, String emotion, String background, int id) {
        this.content = content;
        this.emotion = emotion;
        this.background = background;
        this.id = id;
    }
    public PageDiary(String content, String emotion, String background) {
        this.content = content;
        this.emotion = emotion;
        this.background = background;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageDiary{");
        sb.append("content='").append(content).append('\'');
        sb.append(", emotion='").append(emotion).append('\'');
        sb.append(", background='").append(background).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
