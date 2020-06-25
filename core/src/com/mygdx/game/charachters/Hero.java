package com.mygdx.game.charachters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Coordinator;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.LevelOne;

public class Hero extends  GameObject {

    private Sound attack;

    private LevelOne levelOne;
    private  MainGame mainGame;
    boolean isPressed;
    int keyCode;
    public final static float ATTACK_RANGE=1.7f;

    public enum State{ATTACK, IDLE, JUMP, RUN, HURT};
    public State currentState;
    private State previousState;

    public boolean isTurnedRight() {
        return isTurnedRight;
    }

    private boolean isTurnedRight;


    public boolean isCanAttack() {
        return canAttack;
    }

    private boolean canAttack;

    public long getAttackSignal() {
        return attackSignal;
    }

    private long attackSignal;

    public boolean isInAttackingRange() {
        return inAttackingRange;
    }

    public void setInAttackingRange(boolean inAttackingRange) {
        this.inAttackingRange = inAttackingRange;
    }

    boolean inAttackingRange;

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    Coordinator coordinator;



    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    private boolean canJump;

    Texture[] sheets=new Texture[4];
    //idle - 0
    //run - 1
    //jump - 2
    //attack - 3

    private Animation<TextureRegion> heroIdle;
    private Animation<TextureRegion> heroRun;
    private Animation<TextureRegion> heroJump;
    private Animation<TextureRegion> heroAttack;

    private float stateTime;
    private float stateTimeRun;
    private float stateTimeJump;
    private float stateTimeAttack;

    private static final int MAX_VX=8;
    int i=1;



    public Hound getEnemy() {
        return enemy;
    }

    public void setEnemy(Hound enemy) {
        this.enemy = enemy;
    }

    Hound enemy;

    public Hero(World world, Coordinator coordinator, final LevelOne levelOne) {


        super(world);
        this.coordinator=coordinator;
        this.levelOne = levelOne;
        id ="Hero";
        currentState=State.IDLE;
        currentState=State.IDLE;
        stateTime =0;
        stateTimeAttack=0;
        stateTimeRun=0;
        stateTimeJump=0;
        attackSignal=0;

        isTurnedRight=true;
        attack = Gdx.audio.newSound(Gdx.files.internal("Sounds/attack.mp3"));


        sheets[0]=new Texture("IO/Input/Game/Hero/gothic-hero-idle.png");
        sheets[1]=new Texture("IO/Input/Game/Hero/gothic-hero-run.png");
        sheets[2]=new Texture("IO/Input/Game/Hero/gothic-hero-jump.png");
        sheets[3]=new Texture("IO/Input/Game/Hero/gothic-hero-attack.png");

        TextureRegion[][] sheet=TextureRegion.split(sheets[0], sheets[0].getWidth()/4, sheets[0].getHeight());
        TextureRegion[] frames = new TextureRegion[4];
        for(int i=0;i < 4;i++){
            frames[i]=sheet[0][i];
        }
       heroIdle= new Animation<>(0.25f, frames);

        sheet=TextureRegion.split(sheets[1],sheets[1].getWidth()/12,sheets[1].getHeight());
        frames=new TextureRegion[12];
        for(int i=0;i<12;i++){
            frames[i]=sheet[0][i];
        }
        heroRun=new Animation<TextureRegion>(0.0833f,frames);

        //5
        sheet=TextureRegion.split(sheets[2],sheets[2].getWidth()/5,sheets[2].getHeight());
        frames=new TextureRegion[5];
        for(int i=0;i<5;i++){
            frames[i]=sheet[0][i];
        }
        heroJump=new Animation<TextureRegion>(0.2f,frames);

        //6
        sheet=TextureRegion.split(sheets[3],sheets[3].getWidth()/6,sheets[3].getHeight());
        frames=new TextureRegion[6];
        for(int i=0;i<6;i++){
            frames[i]=sheet[0][i];
        }
        heroAttack=new Animation<TextureRegion>(0.1667f,frames);

        //sprite=new Sprite(new Texture("badlogic.jpg"));
        setBounds(4,8,1,2);
        CircleShape shape=new CircleShape();
        shape.setRadius(0.5f);
        bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        shape.setPosition(new Vector2(0,-0.5f));
        body.createFixture(fixtureDef).setUserData("Legs");

     //   createBody(shape, BodyDef.BodyType.DynamicBody,false);
        shape.setPosition(new Vector2(0,0.5f));
        fixtureDef.shape=shape;

        body.createFixture(fixtureDef);
        shape.dispose();
        MassData massData = body.getMassData();
        massData.mass=80;
        body.setMassData(massData);


        addListener(new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return super.keyTyped(event, character);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
               // Gdx.app.log("", String.valueOf(body.getMass()));
                isPressed=true;
                keyCode=keycode;
                if(keycode== Input.Keys.F){
                    attack.play(0.2f);
                }



                   // multiKeysDown();
                return super.keyDown(event, keycode);
            }



            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                isPressed=false;
                if(keycode== Input.Keys.F){
                    attackSignal+=1;
                }
                if (keycode == Input.Keys.ESCAPE){
                    levelOne.openMenu = true;
                }

                return super.keyUp(event, keycode);
            }
        });

    }

    @Override
    public void act(float delta) {
       if(currentState==State.ATTACK) coordinator.attack();
        move();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       // body.setTransform(getX()+1,getY()+1,0);
        if(body.getLinearVelocity().isZero()){
            currentState=State.IDLE;
        }

        TextureRegion currentFrame=getFrame(Gdx.graphics.getDeltaTime());
        switch(currentState){
            case ATTACK:
                batch.draw(currentFrame, getX()-(getWidth()*0.75f),getY(),getWidth()*3f, getHeight()*1.5f);
                break;
            case JUMP:
                batch.draw(currentFrame, getX()-(getWidth()*0.75f),getY(),getWidth()*2.5f, getHeight()*2f);
                break;
            case RUN:
                batch.draw(currentFrame, getX()-(getWidth()*0.75f),getY(),getWidth()*2.5f, getHeight()*1.5f);
                break;
            case IDLE:
                batch.draw(currentFrame, getX(),getY(),getWidth()*1.5f, getHeight()*1.5f);
                break;
        }
    }
    private TextureRegion getFrame(float dt){
        TextureRegion res;

        switch (currentState){
            case ATTACK:
                //stateTime+=dt;
                res=heroAttack.getKeyFrame(stateTimeAttack);
                break;
            case JUMP:
                //stateTime +=dt;
                res=heroJump.getKeyFrame(stateTimeJump);
                break;
            case RUN:
               // stateTimeRun += dt;
                res=heroRun.getKeyFrame(stateTimeRun,true);
                break;
            case IDLE:
            default:
                //stateTime += dt;
                res=heroIdle.getKeyFrame(stateTime,true);
                break;
        }
        //body.getLinearVelocity().x < 0 ||
        if(( !isTurnedRight) && !res.isFlipX()){
            res.flip(true,false);
            isTurnedRight=false;
            //body.getLinearVelocity().x > 0 ||
        } else if(( isTurnedRight) && res.isFlipX()){
            res.flip(true,false);
            isTurnedRight=true;
        }
        if(stateTimeAttack==0){
            canAttack=true;
            i=0;
        }
        stateTimeJump = currentState==previousState ? stateTimeJump += dt : 0;
        stateTimeRun = currentState==previousState ? stateTimeRun += dt : 0;
        stateTimeAttack = currentState==previousState ? stateTimeAttack += dt : 0;
        stateTime = currentState==previousState ? stateTime += dt : 0;
        previousState=currentState;
        return res;
    }



    private void move(){

        if(isPressed){

            if(keyCode== Input.Keys.D && body.getLinearVelocity().x<MAX_VX){
                isTurnedRight=true;
                body.applyForceToCenter(800,0,true);
                currentState=State.RUN;
            }
            if(keyCode== Input.Keys.A && body.getLinearVelocity().x>-MAX_VX){
                isTurnedRight=false;
                body.applyForceToCenter(-800,0,true);
                currentState=State.RUN;
            }
            if(keyCode== Input.Keys.W && canJump){
                currentState=State.JUMP;
                canJump=false;
                body.applyLinearImpulse(0,700, getX()+ getWidth()/2,
                        getY()+ getHeight()/2,true);
            }

            if(keyCode== Input.Keys.F){
                currentState=State.ATTACK;
                canAttack=false;
               // attack.play();
                if(isTurnedRight){
                    body.applyLinearImpulse(5,0, getX()+ getWidth()/2,
                            getY()+ getHeight()/2,true);
                }else body.applyLinearImpulse(-5,0, getX()+ getWidth()/2,
                        getY()+ getHeight()/2,true);

            }


        }
    }



    public float getCenterX(){
        return body.getPosition().x;
    }
    public float getCenterY(){
        return body.getPosition().y;
    }





}
