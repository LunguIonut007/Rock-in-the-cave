package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lungunaiman.rockinthecave.BaseClass.ScreenStates;
import com.lungunaiman.rockinthecave.BaseClass.TouchListener;
import com.lungunaiman.rockinthecave.Components.Audio;
import com.lungunaiman.rockinthecave.Components.GameAnimation;
import com.lungunaiman.rockinthecave.Components.GameInput;
import com.lungunaiman.rockinthecave.Events.PointEvent;
import com.lungunaiman.rockinthecave.Events.PointListener;
import com.lungunaiman.rockinthecave.Events.TouchEvent;
import com.lungunaiman.rockinthecave.Screens.GameScreen;

public class HUD implements TouchListener, PointListener
{
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    private ExtendViewport extendViewport;
    ScreenStates mainScreenStates;
    GameInput gameInput;

    private int points;
    private ShapeRenderer pauseShapeRenderer;
    private Rectangle resumeRectangle,menuRectangle,pauseRectangle,gemsBoundRectangle;
    private Sprite pauseSprite,resumeSprite,menuSprite;
    private SpriteBatch spriteBatch;
    private Rectangle[] gemsRectangle;
    private Sprite[] gemsSprite;
    private Audio themeAudio;
    public  HUD()
    {
        themeAudio = Audio.getInstance();
        font = new BitmapFont();
        extendViewport = new ExtendViewport(GameScreen.WORLD_WIDTH,GameScreen.WORLD_HEIGHT);
        extendViewport.update((int)GameScreen.WORLD_WIDTH,(int)GameScreen.WORLD_HEIGHT,true);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        pauseRectangle = new Rectangle(1,extendViewport.getWorldHeight()-4,3,3);
        mainScreenStates = ScreenStates.PLAYING;
        gameInput =  GameInput.getInstance();

        pauseShapeRenderer = new ShapeRenderer();
        pauseShapeRenderer.setAutoShapeType(true);

        spriteBatch = new SpriteBatch();

        pauseSprite = new Sprite(GameAssetManager.getInstance().getManager().get("data/PlayerPack.pack", TextureAtlas.class).findRegion("pause"));
        pauseSprite.setBounds(pauseRectangle.x,pauseRectangle.y,pauseRectangle.width,pauseRectangle.height);
        resumeRectangle = new Rectangle(extendViewport.getWorldWidth()/2-5,extendViewport.getWorldHeight()/2-5,10,10);

        resumeSprite = new Sprite(GameAssetManager.getInstance().getManager().get("data/PlayerPack.pack", TextureAtlas.class).findRegion("play"));
        resumeSprite.setBounds(resumeRectangle.x,resumeRectangle.y,resumeRectangle.width,resumeRectangle.height);

        menuSprite = new Sprite(GameAssetManager.getInstance().getManager().get("data/PlayerPack.pack", TextureAtlas.class).findRegion("back"));
        menuRectangle = new Rectangle(extendViewport.getWorldWidth()-7f-2f,0+2f,7,7);
        menuSprite.setBounds(menuRectangle.x,menuRectangle.y,menuRectangle.width,menuRectangle.height);

        GameInput.getInstance().add(this);

        gemsSprite= new Sprite[3];
        gemsRectangle = new Rectangle[4];
        gemsBoundRectangle  = new Rectangle(extendViewport.getWorldWidth()-5f,extendViewport.getWorldHeight()-2f,1,1);
        gemsRectangle[0] = new Rectangle(gemsBoundRectangle);


        for(int i=0;i<3;i++)
        {
            gemsSprite[i] = new Sprite(GameAssetManager.getInstance().getManager().get("data/PlayerPack.pack", TextureAtlas.class).findRegion("gems animatie"));
        }
        for(int i=1;i<4;i++)
        {

            gemsRectangle[i] = new Rectangle(gemsRectangle[i-1].x-2f,gemsRectangle[i-1].y,gemsRectangle[i-1].width,gemsRectangle[i-1].height);
            gemsSprite[i-1].setBounds(gemsRectangle[i].x,gemsRectangle[i].y,gemsRectangle[i].width,gemsRectangle[i].height);
        }




    }

    public ScreenStates getState()
    {
       // Gdx.app.log("HUD","STATE: " + mainScreenStates);
        return mainScreenStates;
    }

    public void setState(ScreenStates state)
    {
      //  Gdx.app.log("A","SET STATE: " + state);
        mainScreenStates = state;
    }
    public void renderPlay(float delta,SpriteBatch spriteBatch)
    {


        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);
        spriteBatch.begin();

        pauseSprite.draw(spriteBatch);

        for(int i=0;i<points;i++)
        {
            gemsSprite[i].draw(spriteBatch);
        }
        spriteBatch.end();

    }

    public void renderPause()
    {
        pauseShapeRenderer.setProjectionMatrix(extendViewport.getCamera().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        pauseShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        pauseShapeRenderer.setColor(0,0,0,0.8f);
        pauseShapeRenderer.rect(0,0,extendViewport.getWorldWidth(),extendViewport.getWorldHeight());
        pauseShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        pauseShapeRenderer.setProjectionMatrix(extendViewport.getCamera().combined);
        pauseShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        pauseShapeRenderer.rect(resumeRectangle.x,resumeRectangle.y,resumeRectangle.width,resumeRectangle.height);
        pauseShapeRenderer.end();

        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);
        spriteBatch.begin();
        resumeSprite.draw(spriteBatch);
        menuSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public void dispose()
    {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }

    public void resizeViewport(int width, int height)
    {
        extendViewport.update(width,height,true);
    }

    @Override
    public void onTouch(TouchEvent ev)
    {
        Vector2 touch =  GameUtils.getTransformed(extendViewport.getCamera(),ev.getX(),ev.getY());

        if(pauseRectangle.contains(touch)  && mainScreenStates.equals(ScreenStates.PLAYING))
        {

            mainScreenStates = ScreenStates.PAUSED;

        }

        if(resumeRectangle.contains(touch) && mainScreenStates.equals(ScreenStates.PAUSED))
        {

            setState(ScreenStates.PLAYING);
        }
        if(menuRectangle.contains(touch) && mainScreenStates.equals(ScreenStates.PAUSED))
        {
            mainScreenStates = ScreenStates.RETURN_TO_MENU;
        }
    }

    @Override
    public void notifyChange(PointEvent e)
    {
        points += e.getChange();
        if(e.getBrute()!=0)
        {
            points = e.getBrute();
        }

        switch (points)
        {
            case 0: themeAudio.stop(); break;
            case 1:themeAudio.stop(); themeAudio.play("coin_first"); break;
            case 2:themeAudio.stop();  themeAudio.play("coin_second"); break;
            case 3:themeAudio.stop();  themeAudio.play("coin_third"); break;
            default: break;
        }
    }
}
