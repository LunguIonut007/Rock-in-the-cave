package com.lungunaiman.rockinthecave.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lungunaiman.rockinthecave.BaseClass.Destroyable;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.BaseClass.PlayerStates;
import com.lungunaiman.rockinthecave.BaseClass.SpawnListener;
import com.lungunaiman.rockinthecave.Components.GameAnimation;
import com.lungunaiman.rockinthecave.Components.GameInput;
import com.lungunaiman.rockinthecave.Components.Physics;
import com.lungunaiman.rockinthecave.Events.PointEvent;
import com.lungunaiman.rockinthecave.Events.PointListener;
import com.lungunaiman.rockinthecave.Events.SpawnEvent;
import com.lungunaiman.rockinthecave.GameClasses.LevelChanger;
import com.lungunaiman.rockinthecave.GameClasses.SpawnSaver;


public class Player extends Entity implements Destroyable,SpawnListener,PointListener
{
    private static final float  MAX_VELOCITY = 27f;
    private static final float MAXAIR_VELOCITY = 4f;
    public static ExtendViewport viewport;

    private GameInput input;
    private PlayerFoot pf;
    private PlayerStates state;
    private GameAnimation walking,jumping;
    private boolean right,left,grounded,jump,destroyed;
    private Fixture fixture;
    private int gems;
    private int sign = 1;


    public Player()
    {
        name = "player";
        this.size = new Vector2(1.3f,3.7f);
        physics = new Physics();
        input = GameInput.getInstance();
        pf = new PlayerFoot(this);
        physics.createBody(BodyDef.BodyType.DynamicBody,80,60);
        physics.addFixture(false,0.1f,physics.shape(size.x,size.y,new Vector2(0,0),0),this);

        pf.createFoot(size);

        fixture = physics.getBody().getFixtureList().get(0);
        state = PlayerStates.ALIVE;


        walking = new GameAnimation("alergat",1/10f,1/6f,physics.getBody().getPosition());
        jumping = new GameAnimation("jump",0.1f,1/6f,physics.getBody().getPosition());
        mainAnimation = new GameAnimation("IDLE",0.1f,1/6f,physics.getBody().getPosition());


    }


    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }

    @Override
    public void update(float delta)
    {
        this.updateAnim(delta);
        this.input();
        this.move();

        viewport.getCamera().position.set(physics.getBody().getPosition().x,physics.getBody().getPosition().y,0);
        viewport.getCamera().update();

    }

    private void updateAnim(float delta)
    {

        if(grounded)
        {
            if(!left && !right)
            {
                mainAnimation.update(physics.getBody().getPosition(),delta,true);
            }
            else
            {
                walking.update(physics.getBody().getPosition(),delta,true);
            }
        }
        else
        {
            jumping.update(physics.getBody().getPosition(),delta,true);
        }
    }

    private void renderAnim(float delta,SpriteBatch spriteBatch)
    {
        if(right)
            sign = 1;
        else if(left)
            sign = -1;

        if(grounded)
        {
            if(!left && !right)
            {
                //Gdx.app.log("PLAYER","idle");
                mainAnimation.render(delta,spriteBatch,sign);
            }
            else
            {
               // Gdx.app.log("PLAYER","walking");
                walking.render(delta,spriteBatch,sign);
            }
        }
        else
        {
           // Gdx.app.log("PLAYER","jumping");
            jumping.render(delta,spriteBatch,sign);
        }


    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        renderAnim(delta,spriteBatch);

    }



    @Override
    public void destroy()
    {
        state = PlayerStates.DEAD;
        destroyed = true;

    }

    private void input()
    {
        left = input.isA();
        right = input.isD();
        jump = input.isW();
        grounded = pf.getGroundState();

    }

    private void move()
    {
        Vector2 pos = physics.getBody().getPosition();
        Vector2 vel = physics.getBody().getLinearVelocity();

        if(!right && !left )
        {
            fixture.setFriction(1500f);

        }

        else
        {
            fixture.setFriction(0f);
        }

        if(right && vel.x < MAX_VELOCITY)
        {
            if(!grounded && vel.x < MAXAIR_VELOCITY)
                physics.getBody().applyLinearImpulse(2f,0,pos.x,pos.y,true);
            else
            {

                physics.getBody().applyLinearImpulse(1f, 0, pos.x, pos.y, true);
            }
        }
        if(left && vel.x > -MAX_VELOCITY)
        {
            if(!grounded && vel.y > MAXAIR_VELOCITY)
                physics.getBody().applyLinearImpulse(-2f,0,pos.x,pos.y,true);
            else
            {

                physics.getBody().applyLinearImpulse(-1f, 0, pos.x, pos.y, true);
            }
        }

        if(jump && grounded)
        {
             physics.getBody().applyLinearImpulse(0,17f,pos.x,pos.y,true);
        }

    }

    @Override
    public void dispose()
    {
        Physics.world.destroyBody(physics.getBody());
    }




    @Override
    public void startInteractingWith(Entity inter)
    {
        if(inter.getName().equals("coins"))
        {
            gems++;
            //Gdx.app.log("PLAYER","SPAWN");
            SpawnSaver.getInstance().getPointEventManager().propagate(new PointEvent(1,0));

            SpawnSaver.getInstance().increaseIndex();

        }
        else if(inter.getName().equals("arrow") || inter.getName().equals("tepi") || inter.getName().equals("spikes"))
        {
            destroy();
        }
        if(inter.getName().equals("mapch"))
        {
            //Gdx.app.log("PLAYER","INTERACT MAP CHANGE");
            if(gems >=3)
            {
                state = PlayerStates.PREPARED;
                LevelChanger.getInstance().setUnlocked(LevelChanger.getInstance().getIndex());
            }
        }
        if(inter.getName().equals("level"))
        {
            if(((LevelTile)inter).getAvailable()=='y')
            {
                LevelChanger.getInstance().setIndex(((LevelTile)inter).getLevel());
                state = PlayerStates.PREPARED;
            }

        }
    }

    @Override
    public void onSpawn(SpawnEvent e)
    {
        physics.getBody().setTransform(e.getX()/8,e.getY()/8,0);
    }

    public PlayerStates getState()
    {
        return state;
    }

    @Override
    public void notifyChange(PointEvent e)
    {
        if(e.getBrute()!=0)
        {
            gems = e.getBrute();
        }
    }
}
