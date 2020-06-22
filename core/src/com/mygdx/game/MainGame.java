package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.LevelOne;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.Menu;

public class MainGame extends Game {

	Menu menu;
	private LevelOne level;
	private LoadingScreen loading;
	public final static float V_HEIGHT=208;
	public final static float V_WIDTH=400;
	public final static float PPM=100;

	@Override
	public void create () {
		setScreen(new Menu(this));

	}

	public void changeScreen(String name){
		switch (name){
			case "loading":
				if(loading==null){
					loading=new LoadingScreen(this);
					setScreen(loading);
				}else{

				}
				break;
			case "menu":
				if(menu==null){
					menu=new Menu(this);
					this.setScreen(menu);
				}
				break;
			case"game":
				if(level==null){
					level=new LevelOne(this);
					this.setScreen(level);
				}
				break;
			case "end":

				break;
		}
	}


}
