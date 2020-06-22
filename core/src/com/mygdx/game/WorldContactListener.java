package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.charachters.Hero;

public class WorldContactListener implements ContactListener {

    Hero hero;
    public WorldContactListener(Hero hero) {
        this.hero=hero;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        if(a.getUserData()=="Legs" || b.getUserData()=="Legs"){
            Fixture legs = a.getUserData() == "Legs" ? a : b;
            Fixture ground = a==legs ? b : a;

            if(ground.getUserData()=="Ground"){
                hero.setCanJump(true);
                Gdx.app.log("Begin contact", "");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
