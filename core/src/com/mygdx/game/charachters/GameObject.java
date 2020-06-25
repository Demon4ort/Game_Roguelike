package com.mygdx.game.charachters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public abstract class GameObject extends Actor {

    public void setHealth(int damage) {
        this.health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = name;
    }

    String id ="GameObject";

    int health;

    public Body getBody() {
        return body;
    }

    Body body;
    BodyDef bodyDef;
    private World world;

    public float getBodyWidth() {
        return bodyWidth;
    }

    public void setBodyWidth(float bodyWidth) {
        this.bodyWidth = bodyWidth;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(float bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    float bodyWidth;
    float bodyHeight;


    public GameObject(World world) {
        this.world = world;
    }

    public Vector2 getPosition00(){
        Vector2 temp=new Vector2();

        temp.x=body.getPosition().x-bodyWidth/2;
        temp.y=body.getPosition().y-bodyHeight/2;
        return temp;
    }
    public Vector2 getPositionCentre(){
        return body.getPosition();
    }
    public Vector2 getPosition0Y(){
        Vector2 temp=new Vector2();
        temp.x=getX();
        temp.y=body.getPosition().y+bodyHeight/2;
        return temp;
    }
    public Vector2 getPositionX0(){
        Vector2 temp=new Vector2();
        temp.x=body.getPosition().x+bodyWidth/2;
        temp.y=getY();
        return temp;
    }
    public void dispose(){
        Array<Fixture> list = body.getFixtureList();
        for(Fixture a:list){
            body.destroyFixture(a);
        }

    }



    protected void createBody(Shape shape, BodyDef.BodyType type, int density,float restitution, float friction, GameObject object){
        bodyDef=new BodyDef();
        bodyDef.position.set(getX()+getWidth()/2,getY()+getHeight()/2);
        bodyDef.type=type;
        body=world.createBody(bodyDef);
        body.setUserData(object);
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
