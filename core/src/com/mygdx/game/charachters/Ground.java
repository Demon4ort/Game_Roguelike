package com.mygdx.game.charachters;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends GameObject {
    public Ground(World world) {
        super(world);

        setBounds(-1,1,25,1);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(12.5f,0.5f);
        createBody(shape, BodyDef.BodyType.StaticBody, 0, 0.1f, 1f);
        super.body.setUserData("Ground");

    }


}
