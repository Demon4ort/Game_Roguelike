package com.mygdx.game.charachters;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends GameObject {
    public Ground(World world, float x, float y, float width, float height) {
        super(world);
        name="Ground";
        setBounds(x,y,width,height);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(width/2,height/2);
        createBody(shape, BodyDef.BodyType.StaticBody, 0, 0.1f, 1f,this);
        super.body.setUserData(this);

    }


}
