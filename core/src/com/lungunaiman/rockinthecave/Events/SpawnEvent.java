package com.lungunaiman.rockinthecave.Events;

public class SpawnEvent
{
    private float x,y;
    public SpawnEvent(float x,float y)
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
