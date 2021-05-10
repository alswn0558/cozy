package com.example.teamproject.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
/**
 * Created by 양민주
 */
public class Schedule implements Serializable {

    public static final String TYPE_DORMITORY = "DORMITORY";
    public static final String TYPE_INDIVIDUAL = "INDIVIDUAL";

    private String id;
    private String writer;
    private String type;
    private Date date;
    private String title;
    private String contents;

    public Schedule(){

    }

    public Schedule(String id) {
        this.id = id;
    }

    public Schedule(String writer, String type, Date date, String title, String contents) {
        this.id = UUID.randomUUID().toString();
        this.writer = writer;
        this.type = type;
        this.date = date;
        this.title = title;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

//    @Override
//    public String toString() {
//        return "Schedule{" +
//                "id='" + id + '\'' +
//                ", writer='" + writer + '\'' +
//                ", type='" + type + '\'' +
//                ", date=" + date +
//                ", title='" + title + '\'' +
//                ", contents='" + contents + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", writer='" + writer + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return getId().equals(schedule.getId()) &&
                getWriter().equals(schedule.getWriter()) &&
                getType().equals(schedule.getType()) &&
                getDate().equals(schedule.getDate()) &&
                getTitle().equals(schedule.getTitle()) &&
                getContents().equals(schedule.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWriter(), getType(), getDate(), getTitle(), getContents());
    }

    public static DiffUtil.ItemCallback<Schedule> scheduleItemCallback = new DiffUtil.ItemCallback<Schedule>() {
        @Override
        public boolean areItemsTheSame(@NonNull Schedule oldItem, @NonNull Schedule newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Schedule oldItem, @NonNull Schedule newItem) {
            return oldItem.equals(newItem);
        }
    };


}
