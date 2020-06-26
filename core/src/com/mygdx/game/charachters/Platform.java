package com.mygdx.game.charachters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends GameObject {
    Sprite sprite;
    public Platform(World world,float x,float y,float width,float height) {
        super(world);
            id ="Platform";
            setBounds(x,y,width,height);
            sprite=new Sprite(new Texture("badlogic.jpg"));
            sprite.setBounds(getX(),getY(),getWidth(),getHeight());
            PolygonShape shape=new PolygonShape();
            shape.setAsBox(width/2,height/2);
            createBody(shape, BodyDef.BodyType.KinematicBody, 1, 0.1f, 1f,this);
            super.body.setUserData(this);
        }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        sprite.setPosition(getX(),getY());
    }

    int x=2;
    @Override
    public void act(float delta) {

        setPosition(getX(), getY()+x*delta);
        if(getY()<1){
            x=2;
        }else if(getY()>=18){
            x=-2;
        }
        body.setTransform(new Vector2(getX()+getWidth()/2,getY()+getHeight()/2), 0);
    }
}





