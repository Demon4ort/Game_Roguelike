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
        this.enemy=(Enemy) actors.get(1);
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




        if((a.getUserData()=="Legs" &&(((GameObject)b.getBody().getUserData()).getName())=="Ground") || (b.getUserData()=="Legs" && (((GameObject)a.getBody().getUserData()).getName())=="Ground")){
            hero.setCanJump(true);
           // Gdx.app.log("Begin contact", "");
        }
        if(((a.getUserData()=="Sword" && (((GameObject)b.getBody().getUserData()).getName())=="Enemy") ||
                (b.getUserData()=="Sword" && (((GameObject)a.getBody().getUserData()).getName())=="Enemy"))){
            inAttackRange=true;
            Gdx.app.log("Trying to attack","");
            hero.setInAttackingRange(inAttackRange);
            hero.setEnemy(enemy);

        }
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End contact", "");
        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        if(((a.getUserData()=="Sword" && b.getBody().getUserData()=="Enemy") ||
                (b.getUserData()=="Sword" && a.getBody().getUserData()=="Enemy"))){

            hero.setInAttackingRange(false);
            hero.setEnemy(null);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
