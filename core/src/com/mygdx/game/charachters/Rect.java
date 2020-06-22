package com.mygdx.game.charachters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Rect extends  GameObject {

    Sprite sprite;
    boolean isPressed;
    int keyCode;
    private boolean isTurnedRight;

    private static final int MAX_VX=8;
    Sword sword;

    public Rect(World world) {
        super(world);
        sprite=new Sprite(new Texture("badlogic.jpg"));
        setBounds(2,7,1,2);
        CircleShape shape=new CircleShape();
        shape.setRadius(0.5f);
        bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        shape.setPosition(new Vector2(0,-0.5f));
        body.createFixture(fixtureDef);
        
     //   createBody(shape, BodyDef.BodyType.DynamicBody,false);
        FixtureDef fixtureDef2 = new FixtureDef();
        shape.setPosition(new Vector2(0,0.5f));
        fixtureDef.shape=shape;

        body.createFixture(fixtureDef);

        MassData massData = body.getMassData();
        massData.mass=80;
        body.setMassData(massData);
        sprite.setBounds( getX(), getY(), getWidth(), getHeight());
        sword=new Sword(world,body.getPosition().x,body.getPosition().y);

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                Gdx.app.log("", String.valueOf(body.getMass()));
                isPressed=true;
                keyCode=keycode;
                if(keycode== Input.Keys.W){
                    //body.applyForceToCenter(0,700,true);
                    body.applyLinearImpulse(0,700, getX()+ getWidth()/2,
                            getY()+ getHeight()/2,true);
                }
                   // multiKeysDown();
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                isPressed=false;
                return super.keyUp(event, keycode);
            }
        });

    }

    @Override
    public void act(float delta) {
        move();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        sword.body.setTransform(body.getPosition(),0);
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       // body.setTransform(getX()+1,getY()+1,0);
        sprite.draw(batch);
    }
    private void move(){
        if(isPressed){
            if(keyCode== Input.Keys.D && body.getLinearVelocity().x<MAX_VX){
                isTurnedRight=true;
                body.applyForceToCenter(800,0,true);
            }
            if(keyCode== Input.Keys.A && body.getLinearVelocity().x>-MAX_VX){
                isTurnedRight=false;
                body.applyForceToCenter(-800,0,true);
            }

        }
    }
    public float getCenterX(){
        return body.getPosition().x;
    }
    public float getCenterY(){
        return body.getPosition().y;
    }





}
