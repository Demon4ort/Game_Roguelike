package com.mygdx.game.charachters;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Sword extends GameObject {

    public NotPlayerCharachter getEnemy() {
        return enemy;
    }

    NotPlayerCharachter enemy;


    public Sword(World world, float x, float y, float width, float height,NotPlayerCharachter enemy, String size ) {
        super(world);
        this.enemy=enemy;
        setBounds(x,y,width, height);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(width/2,height/2);
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.KinematicBody;
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.isSensor=true;
        body=world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(size);
        body.setUserData(this);
        shape.dispose();

    }

    public void setUserData(){

    }
    @Override
    public void act(float delta) {

        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }



}
