package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;

public class MovingSpikes extends MovingPlatform
{

    public MovingSpikes(Vector2 pos, Vector2 size,String spriteName)
    {
        super(pos,size);
        this.name = "spikes";
        mainSprite = new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion(spriteName));

    }

    @Override
    public void update(float delta)
    {

    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        renderSprite(delta,spriteBatch,mainSprite,1/8f,0,1);
    }
    @Override
    public void startInteractingWith(Entity inter)
    {
        if(inter.getName().equals("turnt"))
        {
            change();
            applyVelocity();
        }
    }
}
