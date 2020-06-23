package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.WorldContactListener;
import com.mygdx.game.charachters.Enemy;
import com.mygdx.game.charachters.GameObject;
import com.mygdx.game.charachters.Ground;
import com.mygdx.game.charachters.Hero;

public class LevelOne implements Screen {

    World world;
    MainGame game;
    private OrthographicCamera camera;
    private Stage stage;
    Box2DDebugRenderer renderer;
    Hero hero;
    WorldContactListener worldContactListener;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledRenderer;

    public LevelOne(MainGame game){
        this.game=game;
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/littleMap.tmx");

      //  map = mapLoader.load("maps/lastMap.tmx");

        tiledRenderer = new OrthogonalTiledMapRenderer(map,0.0625f);
        world=new World(new Vector2(0,-10),true);

        renderer = new Box2DDebugRenderer();
        camera=new OrthographicCamera();
        stage=new Stage(new FitViewport(30,22.5f,camera));

        Ground ground;

        for(MapObject e: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject)e).getRectangle();
            ground=new Ground(world,(rect.getX())/16,rect.getY()/16,
                    rect.getWidth()/16 ,rect.getHeight()/16 );

            stage.addActor(ground);
        }





    }



    @Override
    public void show() {
        Box2D.init();

        stage.setDebugAll(true);

        camera.position.set(new Vector2(10,7), 0);
        Array<GameObject> actors=new Array<>();
        Enemy enemy=new Enemy(world);
        hero =new Hero(world);


        stage.addActor(hero);
        stage.addActor(enemy);
        actors.addAll(enemy,hero);
        worldContactListener=new WorldContactListener(actors);

        world.setContactListener(worldContactListener);

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

        tiledRenderer.setView(camera);
        tiledRenderer.render();
        stage.draw();
        camera.position.set(hero.getCenterX(),hero.getCenterY(),0);
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
