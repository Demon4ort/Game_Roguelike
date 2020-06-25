package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainGame;

public class EndScreen implements Screen {

    MainGame game;
    Music music;
    private OrthographicCamera camera;
    private Stage stage;

    public EndScreen(MainGame game) {
        this.game = game;
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

        Label victory=new Label("VICTORY!",skin);
        TextButton play=new TextButton("PLAY",skin);
        TextButton exit = new TextButton("EXIT", skin);
        table.add(victory).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(play).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //music.dispose();
                game.setLevel(null);
                music.pause();
                game.changeScreen("game");
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
        music.dispose();
        stage.dispose();
    }
}
