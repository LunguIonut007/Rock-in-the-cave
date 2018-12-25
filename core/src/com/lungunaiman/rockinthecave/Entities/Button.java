package com.lungunaiman.rockinthecave.Entities;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.lungunaiman.rockinthecave.BaseClass.Destroyable;
import com.lungunaiman.rockinthecave.BaseClass.Entity;
import com.lungunaiman.rockinthecave.BaseClass.TouchListener;
import com.lungunaiman.rockinthecave.Components.GameInput;
import com.lungunaiman.rockinthecave.Components.Physics;
import com.lungunaiman.rockinthecave.Events.ButtonEvent;
import com.lungunaiman.rockinthecave.Events.ButtonEventManager;
import com.lungunaiman.rockinthecave.Events.TouchEvent;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;
import com.lungunaiman.rockinthecave.GameClasses.GameUtils;

public class Button extends Entity implements TouchListener,Destroyable
{
    private int number;
    private ButtonEventManager eventManager;
    private Rectangle rectangle;
    private ShapeRenderer renderer;


    public Button(Vector2 pos,Vector2 size)
    {
        super("button",pos,size, BodyDef.BodyType.StaticBody,true);

        eventManager = ButtonEventManager.getInstance();
        rectangle = new Rectangle(pos.x,pos.y,2*size.x,2*size.y);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        GameInput.getInstance().add(this);
        mainSprite = new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion("buton joc"));
    }

    public void setNumber(int x)
    {
        number = x;
    }

    @Override
    public void update(float delta)
    {

    }
    @Override
    public void render(float delta, SpriteBatch spriteBatch)
    {
        renderSprite(delta,spriteBatch,mainSprite,1/6f,0,1);
    }

    @Override
    public void onTouch(TouchEvent ev)
    {
        if(rectangle.contains(GameUtils.getTransformed(Player.viewport.getCamera(),ev.getX(),ev.getY())))
        {
            eventManager.propagate(new ButtonEvent(number));
            destroy();
        }
    }

    @Override
    public void destroy()
    {
        destroyed  = true;
    }

    @Override
    public void dispose()
    {
        Physics.world.destroyBody(physics.getBody());
    }
}
