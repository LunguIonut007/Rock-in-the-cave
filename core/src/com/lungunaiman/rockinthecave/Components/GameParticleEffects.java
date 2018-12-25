package com.lungunaiman.rockinthecave.Components;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/*
                stadiu beta
*/

public class GameParticleEffects
{
    public ParticleEffect particleEffect;

    public GameParticleEffects(Vector2 pos,String name)
    {
        particleEffect = new ParticleEffect();

        loadParticle(name);
        setPosition(pos);


    }

    public void play()
    {
        particleEffect.start();
        //Gdx.app.log("PARTICLE EFX","PLAY");
    }

    public void loadParticle(String name)
    {
        particleEffect.load(Gdx.files.internal("efx/"+name+".efx"),Gdx.files.internal("efx/"));
    }

    public void setPosition(Vector2 pos)
    {
        particleEffect.getEmitters().first().setPosition(pos.x,pos.y);
    }

    public void render(float delta, SpriteBatch spriteBatch)
    {
        particleEffect.draw(spriteBatch,delta);
    }

    public void update(float delta,int x)
    {
        particleEffect.update(delta);
    }
}
