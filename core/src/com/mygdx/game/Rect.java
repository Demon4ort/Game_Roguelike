package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Rect extends  GameObject {

    Sprite sprite;


    public Rect(World world) {
        super(world);
        sprite=new Sprite(new Texture("badlogic.jpg"));
        setBounds(2,2,2,2);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(2,2);
        createBody(shape, BodyDef.BodyType.KinematicBody,0, 1, 1);
        sprite.setBounds( getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x,body.getPosition().y);
        sprite.setPosition(getX(),getY());
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        addListener(new InputListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                setPosition(x,y);
                return super.mouseMoved(event, x, y);
            }
        });
    }
}
