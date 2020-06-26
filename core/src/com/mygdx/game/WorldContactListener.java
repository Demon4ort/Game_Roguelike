package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.charachters.*;

public class WorldContactListener implements ContactListener {

    Hero hero;
    NotPlayerCharachter enemy;
    Sword sword;
    Coordinator coordinator;

    public WorldContactListener(Array<GameObject> actors, Coordinator coordinator) {
        this.coordinator=coordinator;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture a=contact.getFixtureA();
        Fixture b=contact.getFixtureB();
        //Gdx.app.log(a.getUserData()+":"+b.getUserData(),"");
        if(b.getUserData()=="Legs"){
            hero= (Hero) b.getBody().getUserData();
          //  Gdx.app.log("Hero", String.valueOf(hero.getX()));
       //     Gdx.app.log("Hero", String.valueOf(hero.getY()));
            hero.setCanJump(true);
        }
        if(a.getUserData()=="Enemy"){
            enemy= (NotPlayerCharachter) a.getBody().getUserData();
        }
        if(b.getUserData()=="Enemy"){
            enemy= (NotPlayerCharachter) b.getBody().getUserData();
        }
        if(a.getUserData()=="Ground"){
            if(b.getUserData()=="Enemy"){
                enemy=(NotPlayerCharachter)b.getBody().getUserData();
                enemy.setCanJump(true);
            }
        }
        if(b.getUserData()=="Ground"){
            if(a.getUserData()=="Enemy"){
                enemy=(NotPlayerCharachter)a.getBody().getUserData();
                enemy.setCanJump(true);
            }
        }
        if(a.getUserData()=="Enemy"){
            if(b.getUserData()=="Body"){
                enemy=(NotPlayerCharachter)a.getBody().getUserData();
                hero=(Hero) b.getBody().getUserData();
                if(true ){
                    if(!hero.isAttacking()){
                        hero.setHealth(10);
                        hero.setState(Hero.State.HURT);
                        coordinator.getLevel().setHealth(hero.getHealth());
                        Gdx.app.log("CONTACT", String.valueOf(hero.getHealth()));
                    }
                }
            }
        }
        if(b.getUserData()=="Enemy"){
            if(a.getUserData()=="Body"){
                enemy=(NotPlayerCharachter)b.getBody().getUserData();
                hero=(Hero) a.getBody().getUserData();
                if(true){
                    if(!hero.isAttacking()){
                        hero.setState(Hero.State.HURT);
                        hero.setHealth(10);
                        coordinator.getLevel().setHealth(hero.getHealth());
                        Gdx.app.log("CONTACT", String.valueOf(hero.getHealth()));
                    }
                }
            }
        }


        if(a.getUserData()=="Long"){
            sword=(Sword)a.getBody().getUserData();
            if(b.getUserData()=="Body"){
                coordinator.playerDetected(sword.getEnemy());
            }
        }
        if(b.getUserData()=="Long"){
            sword=(Sword)b.getBody().getUserData();
            if(a.getUserData()=="Body"){
                coordinator.playerDetected(sword.getEnemy());
            }
        }
        if(a.getUserData()=="Short"){
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
       // Gdx.app.log("End contact", a.getUserData()+"+"+b.getUserData());
        if(a.getUserData()=="Long"){
            sword=(Sword)a.getBody().getUserData();
            if(b.getUserData()=="Body"){
                coordinator.chase((Hound) sword.getEnemy());
            }
        }
        if(b.getUserData()=="Long"){
            sword=(Sword)b.getBody().getUserData();
            if(a.getUserData()=="Body"){
                coordinator.chase((Hound) sword.getEnemy());
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
