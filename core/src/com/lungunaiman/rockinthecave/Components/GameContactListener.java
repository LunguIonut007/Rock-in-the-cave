package com.lungunaiman.rockinthecave.Components;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.Entities.Player;

public class GameContactListener implements ContactListener
{
    private static GameContactListener contact;
    private Entity interact1,interact2;

    private GameContactListener() {}

    public void setContactListener()
    {
        Physics.world.setContactListener(this);
    }

    public static GameContactListener getInstance()
    {
        if(contact == null)
            contact = new GameContactListener();
        return contact;
    }

    @Override
    public void beginContact(Contact contact)
    {
        interact1 = (Entity)contact.getFixtureA().getUserData();
        interact2 = (Entity)contact.getFixtureB().getUserData();

        interact1.startInteractingWith(interact2);
        interact2.startInteractingWith(interact1);

      //  Gdx.app.log("C",interact1.getName() + " "+ interact2.getName());

    }

    @Override
    public void endContact(Contact contact)
    {
        interact1 = (Entity)contact.getFixtureA().getUserData();
        interact2 = (Entity)contact.getFixtureB().getUserData();

        interact1.endInteractingWith(interact2);
        interact2.endInteractingWith(interact1);


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
        interact1 = (Entity) contact.getFixtureA().getUserData();
        interact2 = (Entity) contact.getFixtureB().getUserData();

        if (interact1.getName().equals("player") || interact2.getName().equals("player"))
        {
            contact.resetFriction();
        }



        if ((interact1.getName().equals("player") || interact2.getName().equals("player")) && (interact1.getName().equals("jumping") || interact2.getName().equals("jumping")))
        {

            Player player;
            Entity jumpingPlatform;
            if(interact1.getName().equals("player"))
            {
                player = (Player)interact1;
                jumpingPlatform = interact2;
            }
            else
            {
                player = (Player)interact2;
                jumpingPlatform = interact1;
            }

            if(player.getPosition().y - 2*player.getPhysics().getHeight()< jumpingPlatform.getPosition().y)
            {
                contact.setEnabled(false);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }

}
