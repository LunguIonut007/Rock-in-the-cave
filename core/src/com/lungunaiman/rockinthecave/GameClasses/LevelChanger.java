package com.lungunaiman.rockinthecave.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;


public class LevelChanger
{
    private static LevelChanger ourInstance;

    public static LevelChanger getInstance()
    {
        if(ourInstance==null)  ourInstance = new LevelChanger();
        return ourInstance;
    }
    private int index;
    private Array<String> arrayList;
    private Array<Integer> levelUnlocked;
    private FileHandle file;
    Preferences pref;
    private LevelChanger()
    {
        index = 0;
        arrayList = new Array<String>();
        levelUnlocked = new Array<Integer>();
        load();
    }

    private void load()
    {
        file = Gdx.files.internal("data/Levels.txt");
        String s = file.readString();
        String wordsArray[] = s.split("\\r?\\n");

        for (String name : wordsArray)
        {

          arrayList.add(name);
        }

        pref = Gdx.app.getPreferences("Unlocked");

            for(int i=0;i<=5;i++)
            {
               if(!pref.contains(i+""))
                  {
                    pref.putInteger(i + "", 1);
                    pref.flush();
                  }
            }
       // Gdx.app.log("LEVEL CH",);
        pref.putInteger("1",1);
        pref.flush();

        for(int i=0;i<=5;i++)
        {
            levelUnlocked.add(pref.getInteger(i+""));
        }
        for(int i=0;i<=5;i++)
        {
            //Gdx.app.log("LEVEL CHANGER",pref.getInteger(i+"")+"");
        }

    }

    private int getMapIndex()
    {

        index++;

        return (index-1);
    }
    public int getIndex()
    {
        if(index>=arrayList.size)
        index = 0;

        return index;
    }

    public int getIfUnlocked(int x)
    {
        return levelUnlocked.get(x);
    }

    public void setUnlocked(int x)
    {
        levelUnlocked.set(x,1);
        pref.putInteger(x+"",1);
        pref.flush();

    }
    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getLevel()
    {
        return  arrayList.get(getMapIndex());
    }

    public String getLevelMenu() { return arrayList.get(0);}
}
