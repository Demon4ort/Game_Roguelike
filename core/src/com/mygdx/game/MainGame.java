package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.screens.*;

public class MainGame extends Game {

	Menu menu;
	private LoseScreen loseScreen;
	public boolean soundOn;
	public float musicVolume;
	public long startTime;
	public long endTime;

	public void setLevel(LevelOne level) {
		this.level = level;
	}

	public LevelOne getLevel() {
		return level;
	}

	private LevelOne level;
	private LoadingScreen loading;
	private EndScreen endScreen;
	private PauseMenu pauseMenu;
	public final static float V_HEIGHT=208;
	public final static float V_WIDTH=400;
	public final static float PPM=100;

	public int getHardness() {
		return hardness;
	}

	public void setHardness(int hardness) {
		this.hardness = hardness;
	}

	private int hardness;

	private Music music;
	@Override
	public void create () {
		setScreen(new Menu(this));
		endTime=0;
		music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/drumlooper.mp3"));
		music.setLooping(true);
		musicVolume=0.1f;
		music.setVolume(musicVolume);
		soundOn=true;

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
					level=new LevelOne(this,hardness);
					this.setScreen(level);
				}else{
					this.setScreen(level);
				}

				break;
			case"pauseMenu":
				if(pauseMenu==null){
					pauseMenu=new PauseMenu(this);
					this.setScreen(pauseMenu);
				}else{
					this.setScreen(pauseMenu);
				}
				break;
			case "lose":
				if(loseScreen==null){
					loseScreen=new LoseScreen(this);
				}else this.setScreen(loseScreen);
				break;
			case "end":
				if(endScreen==null){
					endScreen=new EndScreen(this);
					this.setScreen(endScreen);
				}else {
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
