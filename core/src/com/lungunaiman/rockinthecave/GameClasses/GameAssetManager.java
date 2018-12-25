package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lungunaiman.rockinthecave.Components.GameAnimation;


public class GameAssetManager
{
    private static GameAssetManager ourInstance = new GameAssetManager();
    private String path;
    public static GameAssetManager getInstance()
    {
        return ourInstance;
    }
    private GameAnimation gameAnimation;
    private AssetManager manager;

    private GameAssetManager()
    {
        manager = new AssetManager();
    }

    public void load()
    {
        path = "data/PlayerPack.pack";

        FileHandle file = Gdx.files.internal("data/Levels.txt");
        String s = file.readString();
        String wordsArray[] = s.split("\\r?\\n");

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        for (String name : wordsArray)
        {
            manager.load("maps/"+name+".tmx",TiledMap.class);
        }


        file = Gdx.files.internal("data/Sounds.txt");
        s = file.readString();
        wordsArray = s.split("\\r?\\n");


        for (String name : wordsArray)
        {
            manager.load("sounds/"+name+".wav",Sound.class);
        }


        manager.load("data/Atlas1.png", Texture.class);
        manager.load("data/PlayerPack.pack", TextureAtlas.class);

    }

    public String getPath()
    {
        return path;
    }

    public boolean loadInMemory()
    {
       loadAnim();
        return manager.update();
    }

    public void clean()
    {
      manager.clear();
    }
    private void loadAnim()
    {
        if(gameAnimation == null)
            gameAnimation = new GameAnimation(Gdx.files.internal("data/LoadingPack.pack"),"loading",0.2f,1/12f);
    }
    public AssetManager getManager()
    {
        return manager;
    }

    public void renderLoadingScreen(float delta,SpriteBatch spriteBatch,Viewport viewport)
    {
        gameAnimation.update(new Vector2(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2),delta,true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        gameAnimation.render(delta,spriteBatch,1);
        spriteBatch.end();
    }
}
