package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Destroyable;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;
import com.lungunaiman.rockinthecave.GameClasses.LevelChanger;


public class FalseTreasure extends Entity implements Destroyable
{
    private boolean interacted,once;
    private float time;
    private Vector2 playerPos;
    private ShapeRenderer renderer;
    public FalseTreasure(Vector2 pos, Vector2 size)
    {
        super("FalseTreasure",pos,size, BodyDef.BodyType.DynamicBody,true);
        name = "FalseTreasure";
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
       // mainSprite.setPosition(pos.x,pos.y);

        //mainSprite.setScale(1/8f);
    }

    @Override
    public void destroy()
    {
        destroyed = true;
    }

    @Override
    public void update(float delta)
    {
        if(interacted)
        {
            if(mainSprite == null)
            {
                mainSprite = new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion("the treasure is a lie"));
                mainSprite.setBounds(pos.x-25,pos.y-5,50,36);
            }

           // Gdx.app.log("FALSETRESURE","Updateing");
            if (time >= 4)
            {
                time -= 4;
                once = true;
            }
            time += delta;

            if(time < 4)
            {
               /* renderer.begin(ShapeRenderer.ShapeType.Filled);
                Gdx.gl.glEnable(GL20.GL_BLEND);
                renderer.setColor(0,0,0,0.8f);
                renderer.rect(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);*/
            }
        }
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        if(interacted && !once)
        {
            //Gdx.app.log("FALSETRESURE","Rendering");
            mainSprite.draw(spriteBatch);


        }
    }

    @Override
    public void startInteractingWith(Entity inter)
    {
        if(inter.getName().equals("player"))
        {
            interacted = true;
            playerPos = inter.getPosition();
        }
    }
}
