package com.example.son_auto.appdiary_1.model;

/**
 * Created by Son-Auto on 3/5/2018.
 */

public class PageDiary {
    private String content, emotion, background, dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public PageDiary() {
    }

    public PageDiary(String content, String emotion, String background, String dateTime, int id) {
        this.content = content;
        this.emotion = emotion;
        this.background = background;
        this.dateTime = dateTime;
        this.id = id;
    }
    public PageDiary(String content, String emotion, String background, String dateTime) {
        this.content = content;
        this.emotion = emotion;
        this.background = background;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageDiary{");
        sb.append("content='").append(content).append('\'');
        sb.append(", emotion='").append(emotion).append('\'');
        sb.append(", background='").append(background).append('\'');
        sb.append(", dateTime='").append(dateTime).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
