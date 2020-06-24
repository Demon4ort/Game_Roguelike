package com.mygdx.game.charachters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Enemy extends GameObject {

    public enum State{ATTACK, IDLE, JUMP, RUN};
    public State currentState;
    private State previousState;

    private boolean isTurnedRight;



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

    public Enemy(World world) {
        super(world);
        Gdx.app.log("Sobaka de?", "HZ");
        name="Enemy";
        health=100;

        currentState= State.IDLE;
        currentState= State.IDLE;
        stateTime =0;
        stateTimeAttack=0;
        stateTimeRun=0;
        stateTimeJump=0;

        isTurnedRight=true;


        sheets[0]=new Texture("IO/Input/Game/HellHound/hell-hound-idle1.png");
        sheets[1]=new Texture("IO/Input/Game/HellHound/hell-hound-jump1.png");
        sheets[2]=new Texture("IO/Input/Game/HellHound/hell-hound-run1.png");

        TextureRegion[][] sheet=TextureRegion.split(sheets[0], sheets[0].getWidth()/6, sheets[0].getHeight());
        TextureRegion[] frames = new TextureRegion[6];
        for(int i=0;i < 6;i++){
            frames[i]=sheet[0][i];
        }
        heroIdle= new Animation<>(0.16777f, frames);

        sheet=TextureRegion.split(sheets[1],sheets[1].getWidth()/6,sheets[1].getHeight());
        frames=new TextureRegion[6];
        for(int i=0;i<6;i++){
            frames[i]=sheet[0][i];
        }
        heroRun=new Animation<TextureRegion>(0.16777f,frames);

        //5
        sheet=TextureRegion.split(sheets[2],sheets[2].getWidth()/5,sheets[2].getHeight());
        frames=new TextureRegion[5];
        for(int i=0;i<5;i++){
            frames[i]=sheet[0][i];
        }
        heroJump=new Animation<TextureRegion>(0.2f,frames);


        //sprite=new Sprite(new Texture("badlogic.jpg"));
        setBounds(9,8,3f,1.5f);
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(1.5f,0.75f);
        bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData("Enemy");
        shape.dispose();
        //   createBody(shape, BodyDef.BodyType.DynamicBody,false);

        MassData massData = body.getMassData();
        massData.mass=40;
        body.setMassData(massData);


    }
    @Override
    public void act(float delta) {
        move();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        if(health<=0){
            body.setActive(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // body.setTransform(getX()+1,getY()+1,0);
       if(health>0) {
           if(body.getLinearVelocity().isZero()){
            currentState=State.IDLE;
        }

        TextureRegion currentFrame=getFrame(Gdx.graphics.getDeltaTime());
        batch.draw(currentFrame, getX()-(getWidth()*0.25f),getY(),getWidth()*1.5f, getHeight()*1.5f);}
       /* switch(currentState){
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

        */
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
        stateTimeJump = currentState==previousState ? stateTimeJump += dt : 0;
        stateTimeRun = currentState==previousState ? stateTimeRun += dt : 0;
        stateTimeAttack = currentState==previousState ? stateTimeAttack += dt : 0;
        stateTime = currentState==previousState ? stateTime += dt : 0;
        previousState=currentState;
        return res;
    }


    private void move(){
        if(body!=null){
            if(true && body.getLinearVelocity().x<MAX_VX){
                isTurnedRight=true;
                body.applyForceToCenter(800,0,true);
                currentState=State.RUN;
            }
            if(true && body.getLinearVelocity().x>-MAX_VX){
                isTurnedRight=false;
                body.applyForceToCenter(-800,0,true);
                currentState=State.RUN;
            }
            if(true && canJump){
                currentState=State.JUMP;
                canJump=false;
                body.applyLinearImpulse(0,700, getX()+ getWidth()/2,
                        getY()+ getHeight()/2,true);
            }

            if(false){
                currentState=State.ATTACK;

                if(isTurnedRight){
                    body.applyLinearImpulse(5,0, getX()+ getWidth()/2,
                            getY()+ getHeight()/2,true);
                }else body.applyLinearImpulse(-5,0, getX()+ getWidth()/2,
                        getY()+ getHeight()/2,true);
            }

        }
    }
}
