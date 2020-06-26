package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

public class Hud {
    public Stage getStage() {
        return stage;
    }

    public Stage stage;
    private Viewport viewport;

    private float health;
    private Integer score;

    private Label healthLable;
    Label scoreLable;

    public Hud(SpriteBatch batch){
        health = 100f;
        score = 0;
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Table table = new Table();
        table.center();
        table.setFillParent(true);
       // healthLable = new Label(String.format("%03", health), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        //scoreLable = new Label(String.format("%06", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        table.add(healthLable).expandX().padTop(10);
        stage.addActor(table);

    }


}
