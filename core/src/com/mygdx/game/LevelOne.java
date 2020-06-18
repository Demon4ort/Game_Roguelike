package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelOne implements Screen {

    World world;
    MainGame game;
    private OrthographicCamera camera;
    private Stage stage;

    public LevelOne(MainGame game){
        this.game=game;
    }

    @Override
    public void show() {
        Box2D.init();
        world=new World(new Vector2(0,-9.8f),true);
        camera=new OrthographicCamera();
        stage=new Stage(new FitViewport(20,15,camera));

        camera.position.set(new Vector2(10,7), 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
