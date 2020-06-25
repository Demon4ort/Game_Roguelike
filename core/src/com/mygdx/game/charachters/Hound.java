package com.mygdx.game.charachters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Coordinator;

public class Hound extends NotPlayerCharachter implements Enemy {
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    Coordinator coordinator;
    Sword swordLong;
    Sword swordShort;

    public boolean isAimFound() {
        return aimFound;
    }

    public void setAimFound(boolean aimFound) {
        this.aimFound = aimFound;
    }

    private boolean aimFound;

    public boolean isCanAttack() {
        return canAttack;
    }

    boolean canAttack;

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public Hound(World world, float x, float y) {
        //x - 9 , y - 8
        super(world, x, y, 3, 1.5f );
        Gdx.app.log("Sobaka de?", "HZ");
        itClass="Hound";
        health=60;
        canAttack=true;
        canJump=true;
        aimFound=false;

        sheets[0]=new Texture("IO/Input/Game/HellHound/hell-hound-idle1.png");
        sheets[1]=new Texture("IO/Input/Game/HellHound/hell-hound-jump1.png");
        sheets[2]=new Texture("IO/Input/Game/HellHound/hell-hound-run1.png");

        TextureRegion[][] sheet=TextureRegion.split(sheets[0], sheets[0].getWidth()/6, sheets[0].getHeight());
        TextureRegion[] frames = new TextureRegion[6];
        for(int i=0;i < 6;i++){
            frames[i]=sheet[0][i];
        }
        idle= new Animation<>(0.16777f, frames);

        sheet=TextureRegion.split(sheets[1],sheets[1].getWidth()/6,sheets[1].getHeight());
        frames=new TextureRegion[6];
        for(int i=0;i<6;i++){
            frames[i]=sheet[0][i];
        }
        run=new Animation<TextureRegion>(0.16777f,frames);

        //5
        sheet=TextureRegion.split(sheets[2],sheets[2].getWidth()/5,sheets[2].getHeight());
        frames=new TextureRegion[5];
        for(int i=0;i<5;i++){
            frames[i]=sheet[0][i];
        }
        jump=new Animation<TextureRegion>(0.2f,frames);


        PolygonShape shape=new PolygonShape();
        shape.setAsBox(getWidth()/2,getHeight()/2);
        setBodyHeight(getHeight());
        setBodyWidth(getWidth());
        bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData("Enemy");
        shape.dispose();

        swordLong=new Sword(world, body.getPosition().x, body.getPosition().y,15, getHeight(), this, "Long");
        swordShort=new Sword(world, body.getPosition().x, body.getPosition().y,5, getHeight(), this, "Short");

        MassData massData = body.getMassData();
        massData.mass=40;
        body.setMassData(massData);


    }


    @Override
    public void act(float delta) {
        move();
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        swordShort.getBody().setTransform(body.getPosition(),0);
        swordLong.getBody().setTransform(body.getPosition(),0);
        swordShort.act(delta);
        swordLong.act(delta);
        if(aimFound){
            coordinator.chase(this);
        }
        if(health<=0){
            body.setActive(false);
            dispose();
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
        //
        if(body.getLinearVelocity().x < 0 ||( isTurnedRight) && res.isFlipX()){
            res.flip(true,false);
            isTurnedRight=true;
            //body.getLinearVelocity().x > 0 ||
        } else if(body.getLinearVelocity().x > 0 ||( !isTurnedRight) && !res.isFlipX()){
            res.flip(true,false);
            isTurnedRight=false;
        }
        stateTimeJump = currentState==previousState ? stateTimeJump += dt : 0;
        stateTimeRun = currentState==previousState ? stateTimeRun += dt : 0;
        stateTimeAttack = currentState==previousState ? stateTimeAttack += dt : 0;
        stateTime = currentState==previousState ? stateTime += dt : 0;
        previousState=currentState;
        if(stateTimeJump>=1 ){
           // Gdx.app.log("Hound", "canAttack=true");
            canAttack=true;
            currentState=State.IDLE;
        }else if(currentState!=State.IDLE){
           // Gdx.app.log("Hound", "canAttack=false");
            canAttack=false;
        }
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


    public void setState(Hound.State state) {
        currentState=state;
    }
}
