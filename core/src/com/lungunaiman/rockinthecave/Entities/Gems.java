package com.lungunaiman.rockinthecave.Entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Destroyable;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.Components.*;

public class Gems extends Entity implements Destroyable
{
    private int index;
    public Gems(Vector2 pos, Vector2 size,int index)
    {
        super("coins",pos,size,BodyDef.BodyType.StaticBody,true);
        name = "coins";
        this.bt = BodyDef.BodyType.StaticBody;
        this.index = index;
        mainAnimation = new GameAnimation("gems animatie",1/20f,1/6f,physics.getBody().getPosition());

    }


    @Override
    public void destroy()
    {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed()
    {

        return destroyed;
    }


    @Override
    public void dispose()
    {
        Physics.world.destroyBody(physics.getBody());
    }

    @Override
    public void update(float delta)
    {
        mainAnimation.update(physics.getBody().getPosition(),delta,true);
    }
    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        mainAnimation.render(delta,spriteBatch,1);
    }

    @Override
    public void startInteractingWith(Entity inter)
    {
        if(inter.getName().equals("player"))
        {
            //Gdx.app.log("GEMS","Played Sound");
            destroy();
        }
    }

}
