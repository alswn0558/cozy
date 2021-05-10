package com.example.teamproject.Board;
/**
 * Created by 신무현
 */

// 댓글 class
public class Com
{
    /*
        commnet -> 댓글 내용
        id -> user 닉네임으로 쓸 예정, 지금은 그냥 게시판 id 값을 가지고 있음
        date -> 댓글 등록 일자
        name -> user 이름
     */

    String comment;
    String id;
    String date;
    String name;
    Com() {};

    Com(String com, String id, String date, String name)
    {
        this.comment = com;
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public String getComment() {return comment;}
    public String getId() {return id;}
    public String getDate() {return date;}
    public String getName() {return name;}

    public void setComment(String comment) {this.comment = comment;}
    public void setId(String id) {this.id = id;}
    public void setDate(String date)
    {
        this.date = date;
    }
    public void setName(String name) { this.name = name; }


}
