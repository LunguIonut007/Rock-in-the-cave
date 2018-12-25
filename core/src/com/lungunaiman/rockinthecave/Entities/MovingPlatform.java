package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.ButtonListener;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.Events.ButtonEvent;
import com.lungunaiman.rockinthecave.Events.ButtonEventManager;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;

public class MovingPlatform extends Entity implements ButtonListener
{
    private Vector2 velocity;
    private int number = -1; // not controlled by button
    private Sprite sprite;
    int sign = 1;
    float scale = 1/8f;


    public MovingPlatform(Vector2 pos,Vector2 size)
    {
       super("moving",pos,size, BodyDef.BodyType.KinematicBody,false);
        velocity = new Vector2(0,0);
        name = "moving";
        ButtonEventManager.getInstance().add(this);

    }

    public void createSprite(String type)
    {
        TextureAtlas tx = GameAssetManager.getInstance().getManager().get("data/PlayerPack.pack", TextureAtlas.class);
        sprite = new Sprite(tx.findRegion("platforma caramizi "+type));
    }



    public void setX(int x)
    {
        velocity.x = x;
    }
    public void setY(int y)
    {
        velocity.y = y;
    }
    public void setNumber(int x) {number = x;}

    public void change()
    {
        velocity.x*=-1;
        velocity.y*=-1;
    }

    public void applyVelocity()
    {
        physics.getBody().setLinearVelocity(velocity);
    }


    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        float angle = 0;

        renderSprite(delta,spriteBatch,sprite,scale,angle,sign);


    }



    @Override
    public void startInteractingWith(Entity inter)
    {
        if(inter.getName().equals("turn"))
        {
            change();
            applyVelocity();
        }
    }


    @Override
    public void notifyPress(ButtonEvent e)
    {
        if(e.getNr() == number)
        {
            applyVelocity();
        }
    }
}
