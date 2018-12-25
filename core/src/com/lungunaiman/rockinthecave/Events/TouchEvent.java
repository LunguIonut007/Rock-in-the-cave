package com.lungunaiman.rockinthecave.Events;

public class TouchEvent
{
    private float x,y;

    public TouchEvent(float x,float y)
    {
        this.x = x;
        this.y = y;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
}
