package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.charachters.Enemy;
import com.mygdx.game.charachters.Hero;

import java.util.LinkedList;


public class Coordinator {

   private World world;
   private Hero hero;
   private Array<Enemy> enemyArray;
   public boolean attackSignal;


    public Coordinator(World world, Hero hero, Array<Enemy> enemyArray) {
        this.world = world;
        this.hero = hero;
        this.enemyArray = enemyArray;
    }
    public void attack() {
       if(true){
            LinkedList<Integer> list = new LinkedList<>();
            int i = 0;
            int x = 100;
            for (Enemy e : enemyArray) {
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
                    Enemy enemy = enemyArray.get(j);
                    float d = (enemy.getPositionCentre().x - hero.getCenterX());
                    enemy.setHealth(20);
                    Gdx.app.log("Enemy", String.valueOf(enemy.getHealth()));
                    enemy.getBody().applyLinearImpulse(new Vector2(d * 300, 100), enemy.getPositionCentre(), true);
                }
            }


        }
    }

}
