package com.lungunaiman.rockinthecave.Entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
/*

stadiu beta
*/

public class ArrowRandomGenerator extends Entity
{
    private static final float GENERATION_TIME = 5f;
    private static ArrowRandomGenerator arrowRandomGenerator;
    private Player player;
    private Array<Arrow> sageti;
    private float elapsedTime;
    private boolean hard;

    public  static ArrowRandomGenerator getInstance()
    {
        if(arrowRandomGenerator == null)
            arrowRandomGenerator = new ArrowRandomGenerator();
        return arrowRandomGenerator;
    }
    private ArrowRandomGenerator()
    {

        elapsedTime = 0;
        sageti = new Array<Arrow>();
    }

    public void setHard(boolean h)
    {
        hard = h;
    }

    public float generatePosition()
    {
        float size = Player.viewport.getScreenHeight();
        return (float)(size*Math.random());
    }

    public float getElapsedTime(float delta)
    {
        if(elapsedTime >=GENERATION_TIME) elapsedTime -=GENERATION_TIME;

        elapsedTime +=delta;
        return elapsedTime;
    }

    public void setPlayer(Player p)
    {
        player = p;
    }

    @Override
    public void update(float delta)
    {

        if(hard)
        {
            if (getElapsedTime(delta) > GENERATION_TIME)
            {
                Arrow arrow = new Arrow(new Vector3(Player.viewport.getScreenWidth(), generatePosition(), 0));
                arrow.setPlayer(player);
                sageti.add(arrow);
            }

            for (Arrow arrow : sageti)
            {
                if (!arrow.destroyed)
                {
                    arrow.update(delta);
                }
            }

            for (Array.ArrayIterator<Arrow> it = (Array.ArrayIterator<Arrow>) sageti.iterator(); it.hasNext(); )
            {
                Arrow arrow = it.next();
                if (arrow.isDestroyed())
                {
                    arrow.dispose();
                    it.remove();
                }
            }
        }
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        if(hard)
        {
            for (Arrow arrow : sageti)
            {
                arrow.render(delta, spriteBatch);
            }
        }
    }
    @Override
    public void dispose()
    {
        sageti = null;
        player = null;
    }


}
