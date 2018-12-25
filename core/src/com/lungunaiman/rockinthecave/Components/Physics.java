package com.lungunaiman.rockinthecave.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lungunaiman.rockinthecave.BaseClass.Entity;

public class Physics
{
    public static World world;
    public static final short GROUND = 0x0001;
    public static final short PLATFORM = 0x0002;

    private Body body;
    private BodyDef bodyDef;
    private float width,height;
    private Fixture fixture;
    public FixtureDef fixtureDef;
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public Physics()
    {

    }
    public Body getBody()
    {
        return body;
    }
    public void createBody( BodyDef.BodyType type,float x,float y)
    {
        bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = false;

        body = world.createBody(bodyDef);

    }


    public Fixture addFixture(boolean isSensor, float friction,PolygonShape shape,Entity userData)
    {
        fixtureDef = new FixtureDef();
        fixtureDef.isSensor = isSensor;
        fixtureDef.friction = friction;
        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);
        shape.dispose();

        return fixture;
    }

    public PolygonShape shape(float hx,float hy,Vector2 center,float angle)
    {
        PolygonShape shape = new PolygonShape();
        width = hx*2; height = hy*2;
        shape.setAsBox(hx,hy,center,angle);

        return shape;
    }



}
