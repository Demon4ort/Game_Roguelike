package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor {

    Sprite sprite;
    Body body;
    private World world;


    public GameObject(World world) {
        this.world = world;
    }


    protected void createBody(Shape shape, BodyDef.BodyType type, int density,float restitution, float friction){
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type=type;
        body=world.createBody(bodyDef);
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.restitution=restitution;
        fixtureDef.density=density;
        fixtureDef.friction=friction;
        body.createFixture(fixtureDef);
    }

    public void move(float x, float y){
        body.setTransform(x-(getWidth()/2),y-(getHeight()/2),0);
    }
}
