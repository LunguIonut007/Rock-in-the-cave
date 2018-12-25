package com.lungunaiman.rockinthecave.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lungunaiman.rockinthecave.BaseClass.TouchListener;
import com.lungunaiman.rockinthecave.Components.GameAnimation;
import com.lungunaiman.rockinthecave.Components.GameContactListener;
import com.lungunaiman.rockinthecave.Components.GameInput;
import com.lungunaiman.rockinthecave.Entities.ArrowRandomGenerator;
import com.lungunaiman.rockinthecave.Events.TouchEvent;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;
import com.lungunaiman.rockinthecave.GameClasses.GameUtils;
import com.lungunaiman.rockinthecave.GameClasses.LevelChanger;


public class MainMenuScreen implements Screen,TouchListener
{
    private ExtendViewport viewport;
    private  SpriteBatch spriteBatch;
    private Rectangle easyRectangle,hardRectangle;
    private  Game game;
   // private  ShapeRenderer renderer;
    private Sprite easySprite,hardSprite;
    private boolean loaded;


    public MainMenuScreen(Game game)
    {
        this.game = game;
        viewport = new ExtendViewport(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT);
        viewport.update((int)GameScreen.WORLD_WIDTH,(int)GameScreen.WORLD_HEIGHT,true);
        spriteBatch = new SpriteBatch();
        easyRectangle = new Rectangle(viewport.getWorldWidth()/2-64/64f-6f,viewport.getWorldHeight()/2-64/16f,64/8f,64/8f);
        hardRectangle = new Rectangle(viewport.getWorldWidth()/2+6f-64/16f,viewport.getWorldHeight()/2-64/16f,64/8f,64/8f);

       // renderer = new ShapeRenderer();
       // renderer.setAutoShapeType(true);
    }

    @Override
    public void show()
    {

        GameAssetManager.getInstance().load();
        GameInput.getInstance().add(this);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0,0,0,1);

        loaded = GameAssetManager.getInstance().loadInMemory();

        if(!loaded)
        {
           GameAssetManager.getInstance().renderLoadingScreen(delta,spriteBatch,viewport);
        }
        else
        {
            if(easySprite == null)
            {
                easySprite =new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion("easy"));
                easySprite.setBounds(easyRectangle.x,easyRectangle.y,easyRectangle.width,easyRectangle.height);

            }
            if(hardSprite == null)
            {
                hardSprite =new Sprite(GameAssetManager.getInstance().getManager().get(GameAssetManager.getInstance().getPath(), TextureAtlas.class).findRegion("hard"));
                hardSprite.setBounds(hardRectangle.x,hardRectangle.y,hardRectangle.width,hardRectangle.height);

            }

            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            spriteBatch.begin();
            easySprite.draw(spriteBatch);
            hardSprite.draw(spriteBatch);
            spriteBatch.end();

          /*  renderer.setProjectionMatrix(viewport.getCamera().combined);
            renderer.begin();
            renderer.rect(easyRectangle.x,easyRectangle.y,easyRectangle.width,easyRectangle.height);
            renderer.rect(hardRectangle.x,hardRectangle.y,hardRectangle.width,hardRectangle.height);
            renderer.end();*/
        }
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height,true);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {
      //  Gdx.app.log("MENU","DISPOSED");
        this.dispose();
    }

    @Override
    public void dispose()
    {
        GameInput.getInstance().remove(this);
        spriteBatch.dispose();

    }

    @Override
    public void onTouch(TouchEvent ev)
    {
        if(easyRectangle.contains(GameUtils.getTransformed(viewport.getCamera(),ev.getX(),ev.getY())))
        {
            ArrowRandomGenerator.getInstance().setHard(false);
            game.setScreen(new GameScreen(LevelChanger.getInstance().getLevel(), game));
        }
        if(hardRectangle.contains(GameUtils.getTransformed(viewport.getCamera(),ev.getX(),ev.getY())))
        {
            ArrowRandomGenerator.getInstance().setHard(true);
            game.setScreen(new GameScreen(LevelChanger.getInstance().getLevel(), game));
        }
    }

}
