package com.mygdx.game.charachters;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Sword extends GameObject {


    public Sword(World world, float x, float y) {
        super(world);
        setBounds(x,y,3.4f, 2);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(1.7f,1);
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.KinematicBody;
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.isSensor=true;
        body=world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("Sword");
        body.setUserData(this);
        shape.dispose();

    }
    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }



}
