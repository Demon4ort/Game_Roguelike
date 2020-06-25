package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.mygdx.game.Coordinator;
import com.mygdx.game.MainGame;
import com.mygdx.game.WorldContactListener;
import com.mygdx.game.charachters.*;

import java.util.ArrayList;

public class LevelOne implements Screen {


    private int enemyNumber;
    public boolean openMenu;
    World world;
    MainGame game;
    private OrthographicCamera camera;
    private Stage stage;
    Box2DDebugRenderer renderer;
    Hero hero;
    WorldContactListener worldContactListener;
    Coordinator coordinator;
    Array<NotPlayerCharachter> enemyArray;
    ArrayList<NotPlayerCharachter> houndArray;
    Array<GameObject> actors;

    Music music;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledRenderer;

    public LevelOne(MainGame game){
        openMenu = false;
        this.game=game;
        music=Gdx.audio.newMusic(Gdx.files.internal("Sounds/drumlooper.mp3"));
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
            music.setLooping(true);
            music.setVolume(0.1f);
            music.play();
        }






    }



    @Override
    public void show() {
        Box2D.init();

        houndArray=new ArrayList<>();
        stage.setDebugAll(true);

        camera.position.set(new Vector2(10,7), 0);
        actors=new Array<>();
        int[] x={3,21,20,4,47,48};
        int[] y={22,21,27,26, 28, 18};
        Hound enemy=new Hound(world, 9, 8);
        Array<Hound> hounds=new Array<>();
        enemyArray=new Array<>();
        for(int i=0;i<x.length;i++){
            enemy=new Hound(world, x[i],y[i]);
            hounds.add(enemy);
            enemyArray.add(enemy);
            stage.addActor(enemy);
            actors.add(enemy);
            houndArray.add(enemy);
        }
        enemyNumber=enemyArray.size;
        //Demon demon=new Demon(world, 10,25);
        hero =new Hero(world,null, this);

        //stage.addActor(demon);
        stage.addActor(hero);
        stage.addActor(enemy);
        actors.addAll(hero);
        worldContactListener=new WorldContactListener(actors);

        world.setContactListener(worldContactListener);

        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Location", String.valueOf(x));
                Gdx.app.log("Location", String.valueOf(y));
                hero.move(x,y);
                super.clicked(event, x, y);
            }
        });
        stage.setKeyboardFocus(hero);
        Gdx.input.setInputProcessor(stage);

        coordinator=new Coordinator(world,hero,enemyArray,houndArray );
        hero.setCoordinator(coordinator);
        for(Hound e:hounds){
            e.setCoordinator(coordinator);
        }
        //test for mark
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
        if(openMenu){
            pause();
        }

        enemyArray=coordinator.getEnemyArray();
        if(hero.getKillCount()==enemyNumber){
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        game.changeScreen("pauseMenu");

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        /*for(GameObject ob:actors){
            ob.dispose();
        }
         */
        stage.clear();
        music.dispose();
        game.changeScreen("end");
    }
}
