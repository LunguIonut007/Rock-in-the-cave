package com.lungunaiman.rockinthecave.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lungunaiman.rockinthecave.GameClasses.GameAssetManager;

public class Audio
{
    private static Audio ourInstance;

    public static Audio getInstance()
    {
        if(ourInstance == null)
            ourInstance = new Audio();
        ourInstance = new Audio();
        ourInstance.stop();
        return ourInstance;
    }

    private Audio()
    {

    }

    public void play(String name)
    {
         GameAssetManager.getInstance().getManager().get("sounds/"+name+".wav",Sound.class).loop();

        //Gdx.app.log("AUDIO","Play");
    }

    public void stop()
    {

        GameAssetManager.getInstance().getManager().get("sounds/coin_first.wav",Sound.class).stop();
        GameAssetManager.getInstance().getManager().get("sounds/coin_second.wav",Sound.class).stop();
        GameAssetManager.getInstance().getManager().get("sounds/coin_third.wav",Sound.class).stop();

       //Gdx.app.log("AUDIO","Stop");
    }

}
