package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainGame;

public class Menu implements Screen {

    MainGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private Music music;

    public Menu(MainGame game) {
        this.game=game;
    }

    @Override
    public void show() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Medieval Melancholy.wav"));
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
        camera=new OrthographicCamera();
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        Skin skin= new Skin(Gdx.files.internal("UI/quantum-horizon/skin/quantum-horizon-ui.json"));

        TextButton play=new TextButton("PLAY",skin);
        final TextButton soundOn=new TextButton("SoundOn",skin);
        TextButton exit = new TextButton("EXIT", skin);
        final Slider sound=new Slider(0.0f,1.0f,0.05f,false,skin);
        final Slider hardness=new Slider(1,5,1,false,skin);
        Label hard=new Label("Hardness", skin);
        Label zvuk=new Label("Volume",skin);
        table.right().add(play).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(zvuk).fillX().uniformX();
        table.row();
        table.add(sound).fillX().uniformX();
        table.row();
        table.add(soundOn).fillX().uniformX();
        table.row();
        table.add(hard);
        table.row();
        table.add(hardness);
        table.row();
        table.add(exit).fillX().uniformX();

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.dispose();
                game.changeScreen("game");
            }
        });
        soundOn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.soundOn=!game.soundOn;
                if(game.soundOn==false){
                    music.dispose();
                }else{
                    music.play();
                }

            }
        });
        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.musicVolume=sound.getValue();
                music.setVolume(sound.getValue());
            }
        });
        hardness.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setHardness((int) hardness.getValue());
                Gdx.app.log("Hardness", String.valueOf(game.getHardness()));
            }
        });
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();

    }
}
