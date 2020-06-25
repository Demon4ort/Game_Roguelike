package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.charachters.*;

public class WorldContactListener implements ContactListener {

    Hero hero;
    NotPlayerCharachter enemy;
    Sword sword;

    public WorldContactListener(Array<GameObject> actors) {
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        Gdx.app.log(a.getUserData()+":"+b.getUserData(),"");
        if(a.getUserData()=="Legs"){
            hero= (Hero) a.getBody().getUserData();
            Gdx.app.log("Hero", String.valueOf(hero.getX()));
            Gdx.app.log("Hero", String.valueOf(hero.getY()));
            hero.setCanJump(true);
        }
        if(b.getUserData()=="Legs"){
            hero= (Hero) b.getBody().getUserData();
            Gdx.app.log("Hero", String.valueOf(hero.getX()));
            Gdx.app.log("Hero", String.valueOf(hero.getY()));
            hero.setCanJump(true);
        }
        if(a.getUserData()=="Enemy"){
            enemy= (NotPlayerCharachter) a.getBody().getUserData();
        }
        if(b.getUserData()=="Enemy"){
            enemy= (NotPlayerCharachter) b.getBody().getUserData();
        }

        if(a.getUserData()=="Sword"){
            sword=(Sword)a.getBody().getUserData();
            if(b.getUserData()=="Enemy"){
                NotPlayerCharachter non=sword.getEnemy();
                if(non.getItClass()=="Hound"){
                    Hound hound=(Hound)non;

                }else if(non.getItClass()=="Demon"){
                    Demon demon=(Demon)non;

                }
            }
        }
        if(a.getUserData()=="Sword"){
            sword=(Sword)a.getBody().getUserData();
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
