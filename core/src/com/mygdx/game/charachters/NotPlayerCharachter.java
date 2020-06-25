package com.mygdx.game.charachters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public abstract class NotPlayerCharachter extends GameObject {
    public enum State{ATTACK, IDLE, JUMP, RUN};
    protected State currentState;
    protected State previousState;

    private int passport;
    private static int number;



    public boolean isTurnedRight() {
        return isTurnedRight;
    }

    public void setTurnedRight(boolean turnedRight) {
        isTurnedRight = turnedRight;
    }

    protected boolean isTurnedRight;

    public String getItClass() {
        return itClass;
    }

    protected String itClass;

    protected String sheetPath1;
    protected String sheetPath2;
    protected String sheetPath3;


    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public boolean isCanJump() {
        return canJump;
    }

    protected boolean canJump;

    Texture[] sheets=new Texture[4];
    //idle - 0
    //run - 1
    //jump - 2
    //attack - 3

    protected Animation<TextureRegion> idle;
    protected Animation<TextureRegion> run;
    protected Animation<TextureRegion> jump;
    protected Animation<TextureRegion> attack;

    protected float stateTime;
    protected float stateTimeRun;
    protected float stateTimeJump;
    protected float stateTimeAttack;


    public static final int MAX_VX=8;
    public NotPlayerCharachter(World world,float x, float y, float width, float height) {
        super(world);
        number++;
        passport=number;
        setBounds(x,y,width,height);
        health=100;

        currentState= State.IDLE;
        currentState= State.IDLE;
        stateTime =0;
        stateTimeAttack=0;
        stateTimeRun=0;
        stateTimeJump=0;

        isTurnedRight=false;


    }
    @Override
    public boolean equals(Object object ){
        NotPlayerCharachter notPlayerCharachter = this;
        NotPlayerCharachter b=(NotPlayerCharachter)object;
        return this.passport==b.passport;
    }


    private TextureRegion getFrame(float dt){
        TextureRegion res;

        switch (currentState){
            case ATTACK:
                //stateTime+=dt;
                res=attack.getKeyFrame(stateTimeAttack);
                break;
            case JUMP:
                //stateTime +=dt;
                res=jump.getKeyFrame(stateTimeJump);
                break;
            case RUN:
                // stateTimeRun += dt;
                res=run.getKeyFrame(stateTimeRun,true);
                break;
            case IDLE:
            default:
                //stateTime += dt;
                res=idle.getKeyFrame(stateTime,true);
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
