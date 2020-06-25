package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.screens.EndScreen;
import com.mygdx.game.screens.LevelOne;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.PauseMenu;

public class MainGame extends Game {

	Menu menu;

	public void setLevel(LevelOne level) {
		this.level = level;
	}

	private LevelOne level;
	private LoadingScreen loading;
	private EndScreen endScreen;
	private PauseMenu pauseMenu;
	public final static float V_HEIGHT=208;
	public final static float V_WIDTH=400;
	public final static float PPM=100;

	private Music music;
	@Override
	public void create () {
		setScreen(new Menu(this));
		music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/drumlooper.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		//music.play();

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
				}else{
					this.setScreen(level);
				}

				break;
			case"pauseMenu":
				if(pauseMenu==null){
					pauseMenu=new PauseMenu(this);
					this.setScreen(pauseMenu);
				}
			case "end":
				if(endScreen==null){
					endScreen=new EndScreen(this);
					this.setScreen(endScreen);
				}
				break;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}
}
