package com.mygdx.game.charachters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor {

    public void setHealth(int damage) {
        this.health -= damage;
    }

    public int getHealth() {
        return health;
    }

    int health;
    Sprite sprite;

    public Body getBody() {
        return body;
    }

    Body body;
    BodyDef bodyDef;
    private World world;


    public GameObject(World world) {
        this.world = world;
    }


    protected void createBody(Shape shape, BodyDef.BodyType type, int density,float restitution, float friction){
        bodyDef=new BodyDef();
        bodyDef.position.set(getX()+getWidth()/2,getY()+getHeight()/2);
        bodyDef.type=type;
        body=world.createBody(bodyDef);
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.restitution=restitution;
        fixtureDef.density=density;
        fixtureDef.friction=friction;

        body.createFixture(fixtureDef);
    }
    protected void createBody(Shape shape, BodyDef.BodyType type, boolean isSensor) {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = type;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor=isSensor;
        body.createFixture(fixtureDef).setUserData("Ground");

    }

    public void move(float x, float y){
        body.setTransform(x-(getWidth()/2),y-(getHeight()/2),0);
    }
}
