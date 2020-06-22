package com.mygdx.game.charachters;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Sword extends GameObject {


    public Sword(World world, float x, float y) {
        super(world);
        setBounds(x,y,4, 2);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(2,1);
        createBody(shape, BodyDef.BodyType.KinematicBody,true);

    }
    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }



}
