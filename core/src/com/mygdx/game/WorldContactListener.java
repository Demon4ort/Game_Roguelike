package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.charachters.GameObject;
import com.mygdx.game.charachters.Hero;
import com.mygdx.game.charachters.Hound;
import com.mygdx.game.charachters.Sword;

public class WorldContactListener implements ContactListener {

    Hero hero;
    Sword sword;
    Hound enemy;

    public boolean isInAttackRange() {
        return inAttackRange;
    }

    boolean inAttackRange;
    public WorldContactListener(Array<GameObject> actors) {
        this.hero= (Hero) actors.get(1);
        sword=hero.getSword();
        this.enemy=(Hound) actors.get(0);
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        Gdx.app.log(a.getUserData()+":"+b.getUserData(),"");
        if(a.getUserData()=="Legs"){
            hero= (Hero) a.getBody().getUserData();
            hero.setCanJump(true);
        }
        if(b.getUserData()=="Legs"){
            hero= (Hero) b.getBody().getUserData();
            hero.setCanJump(true);
        }
        if(a.getUserData()=="Enemy"){
            enemy= (Hound) a.getBody().getUserData();
        }
        if(b.getUserData()=="Enemy"){
            enemy= (Hound) b.getBody().getUserData();
        }



/*
        if((a.getUserData()=="Legs" &&(((GameObject)b.getBody().getUserData()).getName())=="Ground") || (b.getUserData()=="Legs" && (((GameObject)a.getBody().getUserData()).getName())=="Ground")){
            hero.setCanJump(true);
           // Gdx.app.log("Begin contact", "");
        }

 */

    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End contact", "");
        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
