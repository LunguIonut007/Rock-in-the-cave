package com.lungunaiman.rockinthecave.Events;

import com.badlogic.gdx.utils.Array;
import com.lungunaiman.rockinthecave.BaseClass.ButtonListener;



public class ButtonEventManager
{
    private static ButtonEventManager ourInstance;
    private Array<ButtonListener> arrayList;

    public static ButtonEventManager getInstance()
    {
        if(ourInstance==null) ourInstance = new ButtonEventManager();

         return ourInstance;
    }

    private ButtonEventManager()
    {
        arrayList = new Array<ButtonListener>();
    }

    public void add(ButtonListener btl)
    {
        arrayList.add(btl);
    }

    public void remove(ButtonListener btl)
    {
        arrayList.removeValue(btl,false);
    }

    public void propagate(ButtonEvent e)
    {
        for (ButtonListener bt: arrayList)
        {
            bt.notifyPress(e);
        }
    }
}
