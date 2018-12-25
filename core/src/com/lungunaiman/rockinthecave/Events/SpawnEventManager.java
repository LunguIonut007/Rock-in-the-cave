package com.lungunaiman.rockinthecave.Events;

import com.badlogic.gdx.utils.Array;
import com.lungunaiman.rockinthecave.BaseClass.SpawnListener;



public class SpawnEventManager
{
    private Array<SpawnListener> pointArray;

    public SpawnEventManager()
    {
        pointArray = new Array<SpawnListener>();
    }

    public void add(SpawnListener p)
    {
        pointArray.add(p);
    }

    public void propagate(SpawnEvent e)
    {

        for(SpawnListener p : pointArray)
        {
            p.onSpawn(e);
        }
    }
}
