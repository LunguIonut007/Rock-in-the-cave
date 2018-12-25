package com.lungunaiman.rockinthecave.Components;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lungunaiman.rockinthecave.BaseClass.TouchListener;
import com.lungunaiman.rockinthecave.Events.TouchEvent;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;
import com.lungunaiman.rockinthecave.GameClasses.GameUtils;


public class GameInput implements InputProcessor
{
    private boolean a;
    private boolean d;
    private boolean w;
    private Sprite leftSprite,rightSprite,jumpSprite;

    public boolean isD()
    {
        return d;
    }
    public boolean isA() {return a;}
    public boolean isW()
    {
        return w;
    }

    private static GameInput gameInput;

    private DelayedRemovalArray<TouchListener> arrayList;

    public void add(TouchListener tc)
    {
        arrayList.add(tc);
    }
    public void remove(TouchListener tc) { arrayList.removeValue(tc,false);}

    public static GameInput getInstance()
    {
        if(gameInput == null)
        {
            gameInput =new GameInput();
        }
        return gameInput;
    }

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private Rectangle jumpRectangle;
    private Rectangle leftRectangle;
    private Rectangle rightRectangle;
    private ExtendViewport  viewport;

    private int rightPointer,leftPointer,jumpPointer;

    private GameInput()
    {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        viewport = new ExtendViewport(800/16,600/16);
        viewport.update(800/16,600/16,true);


        jumpRectangle = new Rectangle(viewport.getWorldWidth()+4.6f,0.4f,11,11);
        leftRectangle = new Rectangle(0.4f,0.4f,11,11);
        rightRectangle = new Rectangle(13.2f,0.4f,11,11);

        arrayList = new DelayedRemovalArray<TouchListener>();

        spriteBatch = new SpriteBatch();


    }

    public void propagate(int x,int y)
    {
        TouchEvent e = new TouchEvent(x, y);

        for(TouchListener tc : arrayList)
        {
            tc.onTouch(e);
        }
    }

    public void setResize(int width,int height)
{
    viewport.update(width,height,true);
}

    public void update()
    {
        if(Gdx.app.getType().equals(Application.ApplicationType.Android) || Gdx.app.getType().equals(Application.ApplicationType.iOS))
        {

            if(leftSprite == null || rightSprite == null || jumpSprite == null)
            {
                TextureAtlas at = GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class);
                float scale = 1/1.5f;

               leftSprite =  setSprite(at,"left",leftRectangle,scale);
                rightSprite =  setSprite(at,"right",rightRectangle,scale);
               jumpSprite = setSprite(at,"up",jumpRectangle,scale);

            }


            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            spriteBatch.begin();
            leftSprite.draw(spriteBatch);
            rightSprite.draw(spriteBatch);
            jumpSprite.draw(spriteBatch);
            spriteBatch.end();

        }



    }

    public Sprite setSprite(TextureAtlas at,String name,Rectangle rectangle,float scale)
    {
        Sprite s = new Sprite(at.findRegion(name));
        s.setBounds(rectangle.x,rectangle.y,rectangle.width*scale,rectangle.height*scale);
        return s;
    }
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case com.badlogic.gdx.Input.Keys.W: w = true; return true;
            case com.badlogic.gdx.Input.Keys.A : a = true; break;
            case com.badlogic.gdx.Input.Keys.D : d = true; break;
            case Input.Keys.BACK :/* Gdx.app.log("BACK","YES");*/return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case com.badlogic.gdx.Input.Keys.W: w = false; return true;
            case com.badlogic.gdx.Input.Keys.A : a = false; break;
            case com.badlogic.gdx.Input.Keys.D : d = false; break;
            case Input.Keys.BACK : return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        propagate(screenX,screenY);
        if(Gdx.app.getType().equals(Application.ApplicationType.Android) || Gdx.app.getType().equals(Application.ApplicationType.iOS))
        {
            if(jumpRectangle.contains(GameUtils.getTransformed(viewport.getCamera(), screenX, screenY)))
            {
                w = true;
                jumpPointer = pointer;
            }
            if(leftRectangle.contains(GameUtils.getTransformed(viewport.getCamera(), screenX, screenY)))
            {
                a = true;
                leftPointer = pointer;
            }

            if(rightRectangle.contains(GameUtils.getTransformed(viewport.getCamera(), screenX, screenY)))
            {
                d = true;
                rightPointer = pointer;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if(Gdx.app.getType().equals(Application.ApplicationType.Android) || Gdx.app.getType().equals(Application.ApplicationType.iOS))
        {
            if(pointer == jumpPointer)
            {
                w = false;
            }
            if(pointer == leftPointer)
            {
                a = false;
            }
            if(pointer == rightPointer)
            {
                d = false;
            }
        }
            return false;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

}
