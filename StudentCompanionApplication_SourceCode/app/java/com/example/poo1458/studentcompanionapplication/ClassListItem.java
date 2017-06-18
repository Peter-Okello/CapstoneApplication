package com.example.poo1458.studentcompanionapplication;

/**
 * Created by poo1458 on 4/30/17.
 */
public class ClassListItem
{

    public void setHead(String head)
    {
        this.head = head;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String head;
    public String body;

    public ClassListItem(String head, String body)
    {
        this.head = head;
        this.body = body;
    }


    public String getHead()
    {
        return head;
    }

    public String getBody()
    {
        return body;
    }


}
