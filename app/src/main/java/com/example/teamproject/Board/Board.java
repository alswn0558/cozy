package com.example.teamproject.Board;

import java.io.Serializable;

/**
 * Created by 신무현
 */

// 게시판 class
public class Board implements Serializable {
    
    /*
        id -> 게시판과 댓글 연결
        title -> 제목
        content -> 내용
        date -> 등록일자
        whatBoard -> 어느 게시판인지
        name -> 게시판 작성자
     */
    String id;
    String title;
    String content;
    String date;
    String whatBoard;
    String name;

    Board() {}

    Board(String title, String date, String content, String id, String whatboard, String name)
    {

        this.title = title;
        this.date = date;
        this.content = content;
        this.id = id;
        this.whatBoard = whatboard;
        this.name = name;
    }

    public String getTitle() {return title;}
    public String getDate() {return date;}
    public String getContent() {return content;}
    public String getId() {return id;}
    public String getWhatBoard() {return whatBoard;}
    public String getName() {return name;}

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setId(String id){this.id = id;}

    public void setName(String name) {this.name = name;}

}
