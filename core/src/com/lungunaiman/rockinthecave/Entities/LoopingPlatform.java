package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.*;
import com.lungunaiman.rockinthecave.Components.Physics;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;

public class LoopingPlatform extends Entity
{
    private LoopingState state;
    private float time = 0;
    public LoopingPlatform(Vector2 pos, Vector2 size, com.lungunaiman.rockinthecave.BaseClass.LoopingState stt)
    {
        super("diss",pos,size, BodyDef.BodyType.StaticBody,false);
        name = "diss";
        state = LoopingState.onScreen;
        mainSprite = new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion("platforma-dispare"));

        if(stt.equals(LoopingState.offScreen))
        {
            change();
        }
    }

    @Override
    public void update(float delta)
    {
            time += delta;
            if (time >= 2)
            {
                time -=2;
                change();
            }
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        if(state.equals(com.lungunaiman.rockinthecave.BaseClass.LoopingState.onScreen))
        {
            renderSprite(delta, spriteBatch, mainSprite, 1 / 8f,0,1);
        }
    }

    public void change()
    {
        if(state.equals(LoopingState.onScreen))
        {
            state = LoopingState.offScreen;
            erase();
        }
        else
        {
            this.create();
            state = LoopingState.onScreen;
        }
    }

    void erase()
    {

        Physics.world.destroyBody(physics.getBody());
    }
}
