package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Destroyable;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.Components.GameAnimation;
import com.lungunaiman.rockinthecave.Components.Physics;

enum DisappearingPlatformState
{
    untouched,touched
}

public class DisappearingPlatform extends Entity implements Destroyable
{
    private DisappearingPlatformState state;
    private float time = 0;


    public DisappearingPlatform(Vector2 pos,Vector2 size)
    {
        super("diss",pos,size, BodyDef.BodyType.StaticBody,false);
        name = "diss";
        state = DisappearingPlatformState.untouched;
        mainAnimation = new GameAnimation("caramizi stricate",1f,1/8f,physics.getBody().getPosition());
    }


    @Override
    public void startInteractingWith(Entity inter)
    {
            if(inter.getName().equals("foot"))
            {
                state = DisappearingPlatformState.touched;
            }
    }


    @Override
    public void update(float delta)
    {
        if(state.equals(DisappearingPlatformState.touched))
                mainAnimation.update(physics.getBody().getPosition(),delta,false);


        if(state.equals(DisappearingPlatformState.touched))
        {
            time += delta;
            if (time >= 3)
            {
                time -=3;
                destroy();
            }
        }

    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        mainAnimation.render(delta,spriteBatch,1);
    }


    @Override
    public void destroy()
    {
        destroyed = true;
    }

    @Override
    public void dispose()
    {
        Physics.world.destroyBody(physics.getBody());
    }
}
