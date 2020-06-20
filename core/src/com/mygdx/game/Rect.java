package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.IntSet;

public class Rect extends  GameObject {

    Sprite sprite;
    boolean isPressed;
    int keyCode;
    IntSet keys=new IntSet(0);


    public Rect(World world) {
        super(world);
        sprite=new Sprite(new Texture("badlogic.jpg"));
        setBounds(2,7,2,2);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(1,1);
        createBody(shape, BodyDef.BodyType.DynamicBody,1, 0.2f, 1);
        sprite.setBounds( getX(), getY(), getWidth(), getHeight());
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                body.applyForceToCenter(400,300,true);
                isPressed=true;
                if(keys.size<2){
                    keys.add(keycode);
                }else if(keys.size==2){
                    keys.clear();
                    keys.add(keycode);
                }
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
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       // body.setTransform(getX()+1,getY()+1,0);
        sprite.draw(batch);
    }
    private void move(){
        if(isPressed){
            if(keys.size<2){
                if(keyCode== Input.Keys.D){
                    body.setTransform(body.getPosition().x+(10* Gdx.graphics.getDeltaTime()),body.getPosition().y,0);
                }
                if(keyCode== Input.Keys.A){
                    body.setTransform(body.getPosition().x-(10* Gdx.graphics.getDeltaTime()),body.getPosition().y,0);
                }
                if(keyCode== Input.Keys.W){
                    body.setTransform(body.getPosition().x,body.getPosition().y+(10* Gdx.graphics.getDeltaTime()),0);
                }
                if(keyCode== Input.Keys.S){
                    body.setTransform(body.getPosition().x,body.getPosition().y-(10* Gdx.graphics.getDeltaTime()),0);
                }
            }else if(keys.size==2){
                multiKeysDown();
            }
        }
    }
    private void multiKeysDown(){
        if(keys.contains(Input.Keys.D) && keys.contains(Input.Keys.W)){
           /* body.applyForceToCenter(new Vector2(body.getPosition().x + (25*Gdx.graphics.getDeltaTime()) + getWidth()/2
                    , body.getPosition().y+(10*Gdx.graphics.getDeltaTime())
                    + getHeight()/2), true);

            */
        }
    }




}
