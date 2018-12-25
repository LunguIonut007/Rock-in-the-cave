package com.lungunaiman.rockinthecave.Events;


import com.badlogic.gdx.utils.Array;

public class PointEventManager
{
    private Array<PointListener> pointArray;

    public PointEventManager()
    {
        pointArray = new Array<PointListener>();
    }

    public void add(PointListener p)
    {
        pointArray.add(p);
    }

    public void remove(PointListener p)
    {
        pointArray.removeValue(p,false);
    }
    public void propagate(PointEvent e)
    {
        for(PointListener p : pointArray)
        {
            p.notifyChange(e);
        }
    }
}
