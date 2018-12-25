package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.Entities.Button;
import com.lungunaiman.rockinthecave.Entities.DisappearingPlatform;
import com.lungunaiman.rockinthecave.Entities.FalseTreasure;
import com.lungunaiman.rockinthecave.Entities.Gems;
import com.lungunaiman.rockinthecave.Entities.LevelTile;
import com.lungunaiman.rockinthecave.Entities.LoopingPlatform;
import com.lungunaiman.rockinthecave.Entities.MovingPlatform;
import com.lungunaiman.rockinthecave.Entities.MovingSpikes;


public class EntityFactory
{
    public Entity produce(String name, MapObject object)
    {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        Vector2 pos  = new Vector2(rectangle.getX()/8,rectangle.getY()/8);
        Vector2 size = new Vector2(rectangle.getWidth()/16,rectangle.getHeight()/16);

        Entity entity = new Entity("null");

        if(name.equals("coins"))
         {
             int SpawnIndex = SpawnSaver.getInstance().getSpawnIndex();
             int index = Integer.parseInt(object.getProperties().get("index",String.class));
             if(SpawnIndex<index)
                 entity = new Gems(pos,size,index);
        }
        if(name.equals("ground"))
        {
            entity = new Entity("ground",pos,size, BodyDef.BodyType.StaticBody,false);

        }
        if(name.equals("moving"))
        {
            entity =new MovingPlatform(pos,size);
            int speedx = Integer.parseInt( (String)object.getProperties().get("speedx") );
            int speedy = Integer.parseInt( (String)object.getProperties().get("speedy") );
            int nr = Integer.parseInt((String)object.getProperties().get("nr"));

            ((MovingPlatform)entity).setNumber(nr);
            ((MovingPlatform)entity).setX(speedx);
            ((MovingPlatform)entity).setY(speedy);
            ((MovingPlatform)entity).createSprite((String)object.getProperties().get("type"));
            if(nr==-1)
              ((MovingPlatform)entity).applyVelocity();
        }
        if(name.equals("tepi"))
        {
            entity = new Entity("tepi",pos,size, BodyDef.BodyType.StaticBody,false);

        }
        if(name.equals("turn"))
        {
            entity = new Entity("turn",pos,size, BodyDef.BodyType.DynamicBody,true);
            entity.getPhysics().getBody().setGravityScale(0);
        }
        if(name.equals("jumping"))
        {
            entity = new Entity("jumping",pos,size, BodyDef.BodyType.StaticBody,false);

        }

        if(name.equals("diss"))
        {
            String type = object.getProperties().get("type",String.class);
            //Gdx.app.log("S",type);
            if(type.equals("noloop"))
            {
                entity = new DisappearingPlatform(pos, size);
            }
            else
            {
                if(object.getProperties().get("atStart",String.class).equals("visible"))
                         entity = new LoopingPlatform(pos,size, com.lungunaiman.rockinthecave.BaseClass.LoopingState.onScreen);
                else
                        entity = new LoopingPlatform(pos,size, com.lungunaiman.rockinthecave.BaseClass.LoopingState.offScreen);
            }

        }
        if(name.equals("spikes"))
        {
            String spriteName = object.getProperties().get("spritename",String.class);

            entity =new MovingSpikes(pos,size,spriteName);
            int speedx = Integer.parseInt( (String)object.getProperties().get("speedx") );
            int speedy = Integer.parseInt( (String)object.getProperties().get("speedy") );

            ((MovingSpikes)entity).setX(speedx);
            ((MovingSpikes)entity).setY(speedy);
            ((MovingSpikes)entity).applyVelocity();
        }
        if(name.equals("mapch"))
        {
            entity = new Entity("mapch",pos,size, BodyDef.BodyType.StaticBody,true);
        }
        if(name.equals("button"))
        {
            entity = new Button(pos,size);
            int nr = Integer.parseInt((String)object.getProperties().get("nr"));
            ((Button)entity).setNumber(nr);
        }
        if(name.equals("nivel"))
        {
            entity = new LevelTile(pos,size);
            int level = Integer.parseInt((String)object.getProperties().get("name"));
            char c = 'n';
            if(LevelChanger.getInstance().getIfUnlocked(level)==1)
                         c = 'y';

            ((LevelTile)entity).setSprite(String.valueOf(level)+c);
            ((LevelTile)entity).setLevel(level);
            ((LevelTile)entity).setAvailable(c);
        }
        if(name.equals("turnt"))
        {
            entity = new Entity("turnt",pos,size, BodyDef.BodyType.DynamicBody,true);
            entity.getPhysics().getBody().setGravityScale(0);
        }
        if(name.equals("lie"))
        {
            entity = new FalseTreasure(pos,size);
            entity.getPhysics().getBody().setGravityScale(0);
        }
        return entity;
    }


}
