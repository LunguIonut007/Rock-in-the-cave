package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Destroyable;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.BaseClass.TouchListener;
import com.lungunaiman.rockinthecave.Components.*;
import com.lungunaiman.rockinthecave.Events.TouchEvent;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;
import com.lungunaiman.rockinthecave.GameClasses.GameUtils;

/*
    stadiu beta
 */

public class Arrow extends Entity implements Destroyable,TouchListener
{
    Player player;

    private Polygon poly;
    Vector2 posPlayer,posArrow,sol,size;
    float desiredAngle;
    ShapeRenderer shapeRenderer;
    boolean destroyed = false;
    Sprite mainSprite;
    int sign;

    public Arrow( Vector3 p)
    {
        this.name = "arrow";
        physics = new Physics();
        p = Player.viewport.getCamera().unproject(p);
        mainSprite = new Sprite(GameAssetManager.getInstance().getManager().get("data/PlayerPack.pack", TextureAtlas.class).findRegion("sageata"));
        physics.createBody(BodyDef.BodyType.KinematicBody,p.x,p.y);
        //Gdx.app.log("t",p.x+ " " + p.y);
        size = new Vector2(47/16+0.5f,16/16);
        physics.addFixture(false,0, physics.shape(size.x+1,size.y,new Vector2(0,0),0),this);

        sol = new Vector2();

        poly= new Polygon();
        poly.setOrigin(physics.getWidth()/2,physics.getHeight()/2);
        poly.setVertices(new float[]{0,0,0,physics.getHeight(),physics.getWidth(),physics.getHeight(),physics.getWidth(),0});
        GameInput.getInstance().add(this);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

    }

    public void setPlayer(Player p)
    {
        player = p;
    }
    @Override
    public void update(float delta)
    {

        posPlayer = player.getPhysics().getBody().getPosition();
        posArrow = this.physics.getBody().getPosition();

        if(posPlayer.x < posArrow.x)
        {
            sign = 1;
        }
        else
        {
            sign = -1;
        }
        sol.x = posPlayer.x - posArrow.x;
        sol.y = posPlayer.y - posArrow.y;

        sol = sol.nor();
        sol.x *=7;
        sol.y *=7;
      //  desiredAngle = (float)Math.atan2(sol.y,sol.x);
        desiredAngle = 0;
        this.physics.getBody().setTransform(posArrow,desiredAngle);
        this.physics.getBody().setLinearVelocity(sol);


        poly.setPosition(posArrow.x-physics.getWidth()/2,posArrow.y-physics.getHeight()/2);
        poly.setRotation(MathUtils.radiansToDegrees*desiredAngle);


       // shapeRenderer.setProjectionMatrix(Player.viewport.getCamera().combined);
        /*shapeRenderer.begin();

        //shapeRenderer.setColor(139,69,19,1);
        shapeRenderer.polygon(poly.getTransformedVertices());
        shapeRenderer.end();*/




      //  mainSprite.setBounds(posArrow.x,posArrow.y,physics.getWidth(),physics.getHeight());

        //mainSprite.setCenter(physics.getWidth()/2,physics.getHeight()/2);
      // mainSprite.setRotation(desiredAngle*MathUtils.radiansToDegrees);

    }
    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        //Gdx.app.log("desired",desiredAngle+"");

       // mainSprite.draw(spriteBatch);
        this.renderSprite(delta,spriteBatch,mainSprite,1/8f,desiredAngle,sign);

    }
    @Override
    public void dispose()
    {
        Physics.world.destroyBody(physics.getBody());
    }



    @Override
    public void destroy()
    {
        //Gdx.app.log("arrow","Clicked");
        destroyed = true;

    }

    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }

    @Override
    public void startInteractingWith(Entity inter)
    {
        if(inter.getName().equals("player"))
        {
            destroy();
        }
    }



    @Override
    public void onTouch(TouchEvent ev)
    {
        if(poly.contains(GameUtils.getTransformed(Player.viewport.getCamera(),ev.getX(),ev.getY())))
        {
            destroy();
        }

    }


}
