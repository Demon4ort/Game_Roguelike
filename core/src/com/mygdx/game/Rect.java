package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Rect extends  GameObject {

    Sprite sprite;


    public Rect(World world) {
        super(world);
        sprite=new Sprite(new Texture("badlogic.jpg"));
        setBounds(2,2,2,2);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(2,2);
        createBody(shape, BodyDef.BodyType.DynamicBody,0, 0, 1);
        sprite.setBounds( getX(), getY(), getWidth(), getHeight());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       // body.setTransform(getX()+1,getY()+1,0);
        setPosition(body.getPosition().x,body.getPosition().y);
        sprite.setPosition(getX(),getY());
        sprite.draw(batch);
    }



}
