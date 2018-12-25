package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lungunaiman.rockinthecave.Events.PointEvent;
import com.lungunaiman.rockinthecave.Events.PointEventManager;
import com.lungunaiman.rockinthecave.Events.PointListener;
import com.lungunaiman.rockinthecave.Events.SpawnEvent;
import com.lungunaiman.rockinthecave.Events.SpawnEventManager;

public class SpawnSaver implements PointListener
{
    private static SpawnSaver ourInstance;
    private Vector2[] spawnPos;
    private SpawnEventManager spawnEventManager;
    private int spawnIndex;
    private PointEventManager pointEventManager;

    public SpawnEventManager getSpawnEventManager()
    {
        return spawnEventManager;
    }
    public static SpawnSaver getInstance()
    {
        if( ourInstance==null)
            ourInstance = new SpawnSaver();
        return ourInstance;
    }

    private SpawnSaver()
    {
        spawnPos = new Vector2[4];
        spawnEventManager = new SpawnEventManager();
        pointEventManager = new PointEventManager();
        spawnIndex = 0;
    }

    public void addSpawn(Vector2 pos,int index)
    {
        spawnPos[index] = pos;
    }

    @Override
    public void notifyChange(PointEvent e)
    {
        spawnIndex+=e.getChange();
    }

    public void propagate()
    {

        //Gdx.app.log("SPAWNER","OVER THE SAVER");
        pointEventManager.propagate(new PointEvent(0,spawnIndex));
        spawnEventManager.propagate(new SpawnEvent(spawnPos[spawnIndex].x,spawnPos[spawnIndex].y));

    }

    public void reset()
    {
        spawnIndex = 0;
    }

    public int getSpawnIndex()
    {
        return spawnIndex;
    }

    public void increaseIndex()
    {

        spawnIndex++;
    }

    public PointEventManager getPointEventManager()
    {
        return pointEventManager;
    }
}
