package com.example.son_auto.appdiary_1.model;

import java.io.Serializable;

/**
 * Created by Son-Auto on 3/5/2018.
 */

public class PageDiary implements Serializable {
    private String content, emotion, background, editTextBackGround, dateTime;
    private int id;
    private String font, style, color, size, position;

    public PageDiary() {
    }

    public String getEditTextBackGround() {
        return editTextBackGround;
    }

    public void setEditTextBackGround(String editTextBackGround) {
        this.editTextBackGround = editTextBackGround;
    }

    public PageDiary(String emotion, String background, String editTextBackGround, String dateTime, String content, String font, String style, String color, String size, String position, int id) {
        this.emotion = emotion;
        this.background = background;
        this.editTextBackGround = editTextBackGround;
        this.dateTime = dateTime;
        this.content = content;
        this.font = font;
        this.style = style;
        this.color = color;
        this.size = size;
        this.position = position;
        this.id = id;
    }

    public PageDiary(String emotion, String background, String dateTime, String content, String font, String style, String color, String size, String position) {
        this.emotion = emotion;
        this.background = background;
        this.editTextBackGround = editTextBackGround;
        this.dateTime = dateTime;
        this.content = content;
        this.font = font;
        this.style = style;
        this.color = color;
        this.size = size;
        this.position = position;
    }

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageDiary{");
        sb.append("content='").append(content).append('\'');
        sb.append(", emotion='").append(emotion).append('\'');
        sb.append(", background='").append(background).append('\'');
        sb.append(", editTextBackGround='").append(editTextBackGround).append('\'');
        sb.append(", dateTime='").append(dateTime).append('\'');
        sb.append(", id=").append(id);
        sb.append(", font='").append(font).append('\'');
        sb.append(", style='").append(style).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
