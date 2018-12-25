package com.lungunaiman.rockinthecave.Events;

public class PointEvent
{
    private int change,brute;

    public PointEvent(int change,int brute)
    {
        this.change = change;
        this.brute = brute;
    }

    public int getChange()
    {
        return  change;
    }

    public int getBrute()
    {
        return  brute;
    }
}

