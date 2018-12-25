package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameUtils
{

    private GameUtils()
    {

    }

    public static Vector2 getTransformed(Camera camera, float x, float y)
    {
        Vector3 v  = new Vector3(x,y,0);
        camera.unproject(v);
        return new Vector2(v.x,v.y);
    }

}
