package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.lungunaiman.rockinthecave.BaseClass.Entity;



public class EntityUpdater
{
    private Array<Entity> entityList;

    public EntityUpdater()
    {
        entityList  = new Array<Entity>();
    }


    public void addEntity(Entity e)
    {
        entityList.add(e);
    }


    public void render(float delta, SpriteBatch spriteBatch)
    {
        for(Entity entity : entityList)
        {
            entity.render(delta,spriteBatch);
        }
    }

    public void update(float delta)
    {
        for(Entity entity : entityList)
        {
            if(!entity.isDestroyed())
            {
                entity.update(delta);
            }
        }

        for(Array.ArrayIterator<Entity> it = (Array.ArrayIterator<Entity>)entityList.iterator(); it.hasNext();)
        {
           Entity entity = it.next();
            if(entity.isDestroyed())
            {
                entity.dispose();
                it.remove();
            }
        }
    }


    public void dispose()
    {

    }
}
