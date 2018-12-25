package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;

public class SpriteMapRenderer
{
    private OrthographicCamera cam;
    private SpriteBatch batch;
    public SpriteMapRenderer()
    {
        batch = new SpriteBatch();

    }

    public void renderObject(MapObject object)
    {
        if (object instanceof TextureMapObject)
        {
            TextureMapObject obj = (TextureMapObject)object;
            Vector2 pos  = new Vector2(obj.getX()/8,obj.getY()/8);
            Vector2 size = new Vector2(obj.getTextureRegion().getRegionWidth()/16,obj.getTextureRegion().getRegionWidth()/16);

            batch.draw(obj.getTextureRegion(),pos.x,pos.y,size.x*2,size.y*2);
    }


    }

    public void renderObjects(MapLayer tl)
    {
        if(tl!=null)
        {
            batch.setProjectionMatrix(cam.combined);
            batch.begin();
            for (MapObject obj : tl.getObjects())
            {
                renderObject(obj);
            }
            batch.end();
        }
    }

    public void setView(OrthographicCamera cam)
    {
        this.cam = cam;
    }
}
