package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.charachters.Hero;
import com.mygdx.game.charachters.Hound;
import com.mygdx.game.charachters.NotPlayerCharachter;
import com.mygdx.game.screens.LevelOne;

import java.util.LinkedList;


public class Coordinator {

   private World world;
   private Hero hero;

    public Array<NotPlayerCharachter> getEnemyArray() {
        return enemyArray;
    }

    private Array<NotPlayerCharachter> enemyArray;
   public boolean attackSignal;
   private NotPlayerCharachter enemy;

    public LevelOne getLevel() {
        return level;
    }

    private LevelOne level;


    public Coordinator(World world, Hero hero, Array<NotPlayerCharachter> enemyArray, LevelOne level) {
        this.world = world;
        this.hero = hero;
        this.enemyArray = enemyArray;
        this.level=level;
    }


    public void chase(Hound enemy) {
        if (hero.getPosition0Y().y < enemy.getPosition00().y || hero.getPosition00().y > enemy.getPosition0Y().y) {
            enemy.setAimFound(false);
        }
        float d = enemy.getPositionCentre().x - hero.getCenterX();
        enemy.setState(Hound.State.JUMP);
            enemy.setCanJump(false);
                if (d > 0) {

                    enemy.setTurnedRight(true);
                    if (enemy.isCanAttack()) {
                        enemy.getBody().applyLinearImpulse(new Vector2(-400, 100), enemy.getBody().getPosition(), true);
                    }
                } else {
                    enemy.setTurnedRight(false);
                    if (enemy.isCanAttack()) {

                        enemy.getBody().applyLinearImpulse(new Vector2(400, 100), enemy.getBody().getPosition(), true);
                    }
                }


        }


    public void playerDetected(NotPlayerCharachter monster){
       // Gdx.app.log("Coordinator", "Player detected");
        Hound enemy=(Hound)monster;
        enemy.setAimFound(true);
        chase(enemy);
       // float d= enemy.getPositionCentre().x-hero.getCenterX();
        //enemy.setState(Hound.State.JUMP);
        /*if(d>0){
            enemy.setTurnedRight(true);
            if(enemy.isCanAttack()){

                enemy.getBody().applyLinearImpulse(new Vector2(-400, 200), enemy.getBody().getPosition(), true);
            }
        }else{
            enemy.setTurnedRight(true);

            if(enemy.isCanAttack()){

                enemy.getBody().applyLinearImpulse(new Vector2(400, 200), enemy.getBody().getPosition(), true);
            }
        }

         */
    }


    public void heroAttack() {
       if(true){
            LinkedList<Integer> list = new LinkedList<>();
            int i = 0;
            int x = 100;
            for (NotPlayerCharachter e : enemyArray) {
                if (hero.getPosition0Y().y < e.getPosition00().y || hero.getPosition00().y > e.getPosition0Y().y) {
                    i++;
                    continue;
                }
                if (hero.isTurnedRight()) {
                    float temp = e.getPosition00().x - hero.getPositionX0().x;
                    if ((temp) < 0) {
                        i++;
                        continue;
                    }
                    if (temp <= 1.7f) {
                        list.add(i);
                    }
                } else {
                    float temp = hero.getPosition00().x - e.getPositionX0().x;
                    if ((temp) < 0) {
                        i++;
                        continue;
                    }
                    if (temp <= 1.7f) {
                        list.add(i);
                    }
                }
                i++;
            }
            if (hero.isCanAttack()) {
                for (int j : list) {
                    NotPlayerCharachter enemy = enemyArray.get(j);
                    float d = (enemy.getPositionCentre().x - hero.getCenterX());
                    enemy.setHealth(20);
                    Gdx.app.log("Enemy", String.valueOf(enemy.getHealth()));


                    if(enemy.getHealth()<=0){
                        deathSignal(enemy);
                    }
                    enemy.getBody().applyLinearImpulse(new Vector2(d * 100, 100), enemy.getPositionCentre(), true);
                }
            }


        }
    }

    public void deathSignal(NotPlayerCharachter enemy) {
        this.enemy=enemy;
        int i=enemyArray.indexOf(enemy, true);
        enemyArray.removeValue(enemy,true);
        Gdx.app.log("Number", String.valueOf(i));
        hero.plusKill();
    }
}
