package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lungunaiman.rockinthecave.BaseClass.Entity;



public class PlayerFoot extends Entity
{

    private boolean groundState;
    public boolean getGroundState()
    {
        return groundState;
    }

    public PlayerFoot(Player player)
    {
        physics = player.getPhysics();
        name = "foot";

    }

    public void createFoot(Vector2 size)
    {
        physics.addFixture(true,0,physics.shape(size.x-0.1f,0.6f,new Vector2(0,-size.y+0.05f),0),this);

    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
    }

    @Override
    public void update(float delta)
    {

    }

    @Override
    public void startInteractingWith(Entity inter)
    {

        if(inter.getName().equals("moving") || inter.getName().equals("ground"))
        {
            groundState = true;
        }
        if(inter.getName().equals("jumping") || inter.getName().equals("diss") || inter.getName().equals("level"))
        {
            groundState = true;
        }
    }

    @Override
    public void endInteractingWith(Entity inter)
    {

        if(inter.getName().equals("moving") || inter.getName().equals("ground"))
        {
            groundState = false;
        }
        if(inter.getName().equals("jumping") || inter.getName().equals("diss")|| inter.getName().equals("level"))
        {
            groundState = false;
        }

    }

    @Override
    public String getName()
    {
        return name;
    }
}
