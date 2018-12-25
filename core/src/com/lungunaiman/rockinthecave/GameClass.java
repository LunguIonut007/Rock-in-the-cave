package com.lungunaiman.rockinthecave;


import com.badlogic.gdx.Game;
import com.lungunaiman.rockinthecave.Screens.MainMenuScreen;

public class GameClass extends Game
{
	@Override
	public void create()
	{
		setScreen(new MainMenuScreen(this));
	}
}
