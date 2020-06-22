package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.WorldContactListener;
import com.mygdx.game.charachters.Ground;
import com.mygdx.game.charachters.Hero;

public class LevelOne implements Screen {

    World world;
    MainGame game;
    private OrthographicCamera camera;
    private Stage stage;
    Box2DDebugRenderer renderer;
     Hero hero;

    public LevelOne(MainGame game){
        this.game=game;
    }



    @Override
    public void show() {
        Box2D.init();
        world=new World(new Vector2(0,-10),true);

        renderer = new Box2DDebugRenderer();
        camera=new OrthographicCamera();
        stage=new Stage(new FitViewport(30,22.5f,camera));
        stage.setDebugAll(true);

        camera.position.set(new Vector2(10,7), 0);
         hero =new Hero(world);
        world.setContactListener(new WorldContactListener(hero));
        Ground ground=new Ground(world);
        stage.addActor(ground);
        stage.addActor(hero);
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("cc");
                hero.move(x,y);
                super.clicked(event, x, y);
            }
        });
        stage.setKeyboardFocus(hero);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
        camera.position.set(hero.getCenterX(),camera.position.y,0);
        renderer.render(world, camera.combined);
        world.step(1/60f, 6,2);
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
