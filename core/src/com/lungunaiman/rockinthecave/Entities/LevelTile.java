package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;

public class LevelTile extends Entity
{
    private int level;

    public char getAvailable()
    {
        return available;
    }

    public void setAvailable(char available)
    {
        this.available = available;
    }

    private char available;


    public LevelTile(Vector2 pos, Vector2 size)
    {
        super("level",pos,size, BodyDef.BodyType.StaticBody,false);
        name = "level";

    }


    public void setLevel(int x)
    {
        level = x;
    }

    public int getLevel()
    {
        return level;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
       // mainSprite.draw(spriteBatch);
        renderSprite(delta,spriteBatch,mainSprite,1/8f,0,1);
    }

    public void setSprite(String name)
    {
        mainSprite = new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion(name));

    }

}
