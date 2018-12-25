package com.lungunaiman.rockinthecave.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lungunaiman.rockinthecave.BaseClass.PlayerStates;
import com.lungunaiman.rockinthecave.BaseClass.ScreenStates;
import com.lungunaiman.rockinthecave.Components.Audio;
import com.lungunaiman.rockinthecave.Components.*;
import com.lungunaiman.rockinthecave.Entities.*;
import com.lungunaiman.rockinthecave.Events.SpawnEvent;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;
import com.lungunaiman.rockinthecave.GameClasses.HUD;
import com.lungunaiman.rockinthecave.GameClasses.LevelChanger;
import com.lungunaiman.rockinthecave.GameClasses.MapLoader;
import com.lungunaiman.rockinthecave.GameClasses.SpawnSaver;

public class GameScreen implements Screen
{
    public static final float WORLD_WIDTH = 800/16;
    public static final float WORLD_HEIGHT = 600/16;
    public static final Vector2 GRAVITY = new Vector2(0,-100);

    public static ArrowRandomGenerator arrowRandomGenerator;
    private Player player;
    private MapLoader mapLoader;
    private Box2DDebugRenderer box2DDebugRenderer;
    private EntityUpdater entityUpdater;
    private HUD hud;
    private Game game;
    private String levelName;
    private ScreenStates state;
    private SpriteBatch spriteBatch;

    public GameScreen(String levelName,Game game)
    {
        Physics.world = new World(GRAVITY,true);
        Player.viewport = new ExtendViewport(WORLD_WIDTH,WORLD_HEIGHT);

        this.game = game;
        this.levelName = levelName;
        this.state = ScreenStates.PLAYING;


        hud = new HUD();
        player = new Player();
        spriteBatch = new SpriteBatch();
        entityUpdater = new EntityUpdater();
        mapLoader = new MapLoader(entityUpdater);
        box2DDebugRenderer = new Box2DDebugRenderer();
        arrowRandomGenerator = ArrowRandomGenerator.getInstance();
        arrowRandomGenerator.setPlayer(player);
    }

    @Override
    public void show()
    {
        ((OrthographicCamera)Player.viewport.getCamera()).zoom+=0.09f;

        SpawnSaver.getInstance().getSpawnEventManager().add(player);

        mapLoader.loadMap(levelName);
        entityUpdater.addEntity(player);

        Audio.getInstance().stop();
        SpawnSaver.getInstance().getPointEventManager().add(player);
        SpawnSaver.getInstance().getPointEventManager().add(hud);
        SpawnSaver.getInstance().propagate();

        GameContactListener.getInstance().setContactListener();

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0,0,0,1);

        renderGame(delta,spriteBatch);
      // box2DDebugRenderer.render(Physics.world,Player.viewport.getCamera().combined);



        switch(state)
        {
            case GAME_OVER:updateGameOver(delta); break;
            case NEXT_LEVEL: updateNextLevel(delta); break;
            case PAUSED: updatePause(delta); break;
            case PLAYING: updatePlaying(delta);break;
            case RETURN_TO_MENU: returnToMenu(delta); break;
        }


    }

    void updatePlaying(float delta)
    {
        Physics.world.step(delta,2,6);
        entityUpdater.update(delta);
        arrowRandomGenerator.update(delta);
        state = hud.getState();


        if(player.getState().equals(PlayerStates.DEAD))
        {
            state = ScreenStates.GAME_OVER;
        }

        if(player.getState().equals(PlayerStates.PREPARED))
        {
            state = ScreenStates.NEXT_LEVEL;
        }


        GameInput.getInstance().update();

    }
    void renderGame(float delta,SpriteBatch spriteBatch)
    {
        mapLoader.renderMap(new int[] { 0, 1,2,3 });
        mapLoader.renderObj("decoru");

        spriteBatch.setProjectionMatrix(Player.viewport.getCamera().combined);
        spriteBatch.begin();
        entityUpdater.render(delta,spriteBatch);
        spriteBatch.end();

        mapLoader.renderMap(new int[] { 5,6,8});
        mapLoader.renderObj("decoro");

        spriteBatch.begin();
        arrowRandomGenerator.render(delta,spriteBatch);
        spriteBatch.end();

        hud.renderPlay(delta, spriteBatch);

    }

    void updateGameOver(float delta)
    {
       game.setScreen(new GameScreen(levelName,game));
    }

    void updatePause(float delta)
    {
        hud.renderPause();

        state = hud.getState();

    }
    void updateNextLevel(float delta)
    {
        SpawnSaver.getInstance().reset();
        game.setScreen(new GameScreen(LevelChanger.getInstance().getLevel(),game));
    }

    @Override
    public void resize(int width, int height)
    {
        GameInput.getInstance().setResize(width,height);
        hud.resizeViewport(width, height);
        Player.viewport.update(width, height,true);
    }

    @Override
    public void pause()
    {
        //Gdx.app.log("SCREEN","PAUSED");
        state = ScreenStates.PAUSED;
    }

    @Override
    public void resume()
    {
       // Gdx.app.log("SCREEN","RESUME");
        state = ScreenStates.PLAYING;
    }

    @Override
    public void hide()
    {
        //Gdx.app.log("SCREEN","HIDE");
        this.dispose();
    }

    @Override
    public void dispose()
    {
        hud.dispose();
        entityUpdater.dispose();
        spriteBatch.dispose();
        mapLoader.dispose();
    }

    public void returnToMenu(float delta)
    {
        Audio.getInstance().stop();
        SpawnSaver.getInstance().reset();
        game.setScreen(new GameScreen(LevelChanger.getInstance().getLevelMenu(),game));
    }

}
