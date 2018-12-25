package com.lungunaiman.rockinthecave.BaseClass;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.Components.GameAnimation;
import com.lungunaiman.rockinthecave.Components.Physics;

public class Entity
{
   protected Physics physics;
   protected String name;
   protected boolean destroyed;
   protected Vector2 pos;
   protected Vector2 size;
   protected BodyDef.BodyType bt;
   protected boolean isSensor;
   protected Sprite mainSprite;
   protected GameAnimation mainAnimation;

   public Entity()
   {

   }

   public Entity(String name,Vector2 pos, Vector2 size, BodyDef.BodyType bt,boolean isSensor)
   {
       this.pos = pos;
       this.size = size;
       this.bt = bt;
       this.name = name;
       this.isSensor = isSensor;
       this.physics = new Physics();
       create();
   }

   public void create()
   {
       getPhysics().createBody(bt,pos.x+size.x,pos.y+size.y);
       getPhysics().addFixture(isSensor,0.1f,getPhysics().shape(size.x,size.y,new Vector2(0,0),0), this);
   }

   public Entity(String name)
   {
       this.name = name;
   }

   public String getName()
   {
       return name;
   }
   public Physics getPhysics() { return physics; }
   public boolean isDestroyed() { return destroyed; }

   public Vector2 getPosition()
   {
       return physics.getBody().getPosition();
   }

   protected void renderSprite(float delta,SpriteBatch spriteBatch,Sprite sprite,float scale,float angle,float sign)
   {

       spriteBatch.draw(sprite,
               physics.getBody().getPosition().x - sign* sprite.getRegionWidth()/2*scale,
               physics.getBody().getPosition().y - sprite.getRegionHeight()/2*scale,
               0,0,
               sprite.getRegionWidth(),
               sprite.getRegionHeight(),
               sign*scale,scale,angle);
       //-sign* sprite.getRegionWidth()/2*scale - sprite.getRegionHeight()/2*scale

   }


   public void dispose()
   {

   }

   public void render(float delta, SpriteBatch spriteBatch)
   {

   }
   public void update(float delta)
   {

   }

   public void startInteractingWith(Entity entity)
   {

   }

   public void endInteractingWith(Entity entity)
   {

   }

}
