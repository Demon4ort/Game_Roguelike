package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {

    SpriteBatch batch;
    Texture texture;
    MainGame game;
    private OrthographicCamera camera;
    private Stage stage;

    public Menu(MainGame game) {
        this.game=game;
    }

    @Override
    public void show() {
        batch=new SpriteBatch();
        texture=new Texture("badlogic.jpg");
        camera=new OrthographicCamera();
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        Skin skin= new Skin(Gdx.files.internal("quantum-horizon/skin/quantum-horizon-ui.json"));
        //"rusty-robot/skin/rusty-robot-ui.json"

        TextButton play=new TextButton("PLAY",skin);
        TextButton chooseSave=new TextButton("Choose Save",skin);
        TextButton exit = new TextButton("EXIT", skin);
        table.right().add(play).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(chooseSave).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    }
}
