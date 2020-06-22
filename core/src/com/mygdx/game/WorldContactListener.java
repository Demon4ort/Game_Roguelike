package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.charachters.Enemy;
import com.mygdx.game.charachters.GameObject;
import com.mygdx.game.charachters.Hero;
import com.mygdx.game.charachters.Sword;

public class WorldContactListener implements ContactListener {

    Hero hero;
    Sword sword;
    Enemy enemy;

    public boolean isInAttackRange() {
        return inAttackRange;
    }

    boolean inAttackRange;
    public WorldContactListener(Array<GameObject> actors) {
        this.hero= (Hero) actors.get(2);
        sword=hero.getSword();
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        Gdx.app.log(a.getUserData()+":"+b.getUserData(),"");
        if(a.getUserData()=="Legs"){
            hero= (Hero) a.getBody().getUserData();
        }
        if(b.getUserData()=="Legs"){
            hero= (Hero) b.getBody().getUserData();
        }
        if(a.getUserData()=="Enemy"){
            enemy= (Enemy) a.getBody().getUserData();
        }
        if(b.getUserData()=="Enemy"){
            enemy= (Enemy) b.getBody().getUserData();
        }
        /*if(a.getUserData()=="Legs" || b.getUserData()=="Legs"){
            Fixture legs = a.getUserData() == "Legs" ? a : b;
            Fixture ground = a==legs ? b : a;
            hero.setCanJump(true);
            Gdx.app.log("Begin contact", "");
            if(ground.getUserData()=="Ground"){

            }
        }

         */

        if((a.getUserData()=="Legs" && b.getBody().getUserData()=="Ground") || (b.getUserData()=="Legs" && a.getBody().getUserData()=="Ground")){
            hero.setCanJump(true);
           // Gdx.app.log("Begin contact", "");
        }
        if(((a.getUserData()=="Sword" && b.getUserData()=="Enemy") || (b.getUserData()=="Sword" && a.getUserData()=="Enemy"))){
            inAttackRange=true;
            Gdx.app.log("Trying to attack","");
            if(hero.isAttacking()){

            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End contact", "");
        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        if(((a.getUserData()=="Sword" && b.getUserData()=="Enemy") || (b.getUserData()=="Sword" && a.getUserData()=="Enemy"))){
            inAttackRange=false;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
