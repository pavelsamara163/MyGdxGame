package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Other.Laser;
import com.mygdx.game.Screens.PlayScreen;


public class Ship extends Sprite {


    public enum State{FALLING,STANDING,RUNNING,UP,DOWN,SPEED,GROWING,DEAD}
    public  State currentState;
    private State previousState;
     boolean isDestroy;

    public World world;
    public Body b2body;


    private TextureRegion shipStand;
    private TextureRegion shipFlashs;
    private Animation shipRun;
    private TextureRegion shipBigRun;

    private Animation shipDead;
    private Animation shipUP;
    private TextureRegion shipBigUP;
    private Animation shipDOWN;
    private TextureRegion shipBigDOWN;
    private Animation shipFlash ;
    private float stateTimer;
    private boolean runningRight;
    private PlayScreen screen;
    private Array<Laser> laserShoot;

    private boolean setDestroyd ;
    private   boolean shipIsBig;
    private  boolean runGrowAnimation;
    private TextureRegion bigShipStand;
    private Animation growShip;
    private boolean timeToDefineBigShip;
    private boolean timeToRedefineShip;
    private boolean shipIsDead;


    public Ship(PlayScreen screen ){
        this.screen = screen;
        this.world = screen.getWorld();
       currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;




       Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"), i * 130, 152, 130, 76));//x , y ,Ширина,Высота
                shipRun = new Animation(0.1f, frames);
                frames.clear();

        for(int i = 1; i < 3 ;i++)
            frames.add(new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"),i * 130,0,130,76));
        shipUP = new Animation(0.1f,frames);
        frames.clear();

        for(int i = 1; i < 4 ;i++)
            frames.add(new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"),i * 130,76,130,76));
        shipDOWN = new Animation(0.1f,frames);
        frames.clear();

        for (int e = 0; e < 63; e++)
            frames.add(new TextureRegion(screen.getAtlas3().findRegion("Exp_type_B"), e * 63, 0, 58, 58));
        shipDead  = new Animation(0.050f, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"), 0, 0, 136, 76));
        frames.add(new TextureRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"), 0, 0, 136, 76));
        frames.add(new TextureRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460));
        growShip = new Animation(0.2f,frames);


        shipBigDOWN = new TextureRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460);

        shipStand = new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"),0,0,136,76 );
        bigShipStand = new TextureRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460);

        shipBigUP = new TextureRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460);

        shipBigRun = new TextureRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460);

        defineShip();
        setBounds(0, 0, 64 / MyGdxGame.PPM, 64 / MyGdxGame.PPM);
        setRegion(shipStand);

        laserShoot = new Array<Laser>();

    }



    public void update(float dt){
        stateTimer += dt;
        if(shipIsBig)
            setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2  );
            else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion(getFrame(dt));


        if(timeToDefineBigShip)
            defineBigShip();
        if(timeToRedefineShip)
            redefineShip();



        for(Laser  ball : laserShoot) {
            ball.update(dt);
            if (ball.isDestroyed())
                laserShoot.removeValue(ball, true);
        }
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = shipDead.getKeyFrame(stateTimer) ;
                break;
            case GROWING:
                region = growShip.getKeyFrame(stateTimer);
                if(growShip.isAnimationFinished(stateTimer)){
                    runGrowAnimation = false;}
                break;
            case UP:
                region = shipIsBig ? shipBigUP : shipUP.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = shipIsBig ?  shipBigRun :  shipRun.getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = shipIsBig ? shipBigDOWN : shipDOWN.getKeyFrame(stateTimer,true) ;
                break;
            case FALLING:
            case STANDING:
            default:
                region = shipIsBig ? bigShipStand : shipStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
            } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
                region.flip(true, false);
                runningRight = true;
            }

            stateTimer = currentState == previousState ? stateTimer + dt : 0;
            previousState = currentState;
            return region;
        }


    private State getState() {
        if(shipIsDead)
            return State.DEAD;
       else if(runGrowAnimation )
            return State.GROWING;
        else if  (b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState == State.SPEED)
            return State.UP;
        else if (b2body.getLinearVelocity().y < 0 || b2body.getLinearVelocity().y < 0 && previousState == State.SPEED)
            return State.DOWN;
       else if (b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState == State.SPEED)
            return State.SPEED;
        else if (b2body.getLinearVelocity().y < 0 || b2body.getLinearVelocity().y < 0 && previousState == State.SPEED)
            return State.SPEED;
        else if (b2body.getLinearVelocity().y != 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public void grow(){
        runGrowAnimation = true;
        shipIsBig = true;
        timeToDefineBigShip = true;
        setBounds(getX(), getY(), getWidth()+ 88 / MyGdxGame.PPM  , getHeight()+ 88/ MyGdxGame.PPM);

    }

    public boolean  isBig(){
        return shipIsBig;
    }

    public boolean isDead(){
        return shipIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void hit(){
        if(shipIsBig){
            shipIsBig = false ;
            timeToRedefineShip = true;
            setBounds(getX(), getY(), getWidth() / 2,getHeight() / 2);

        }
        else {
            shipIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = MyGdxGame.NOTHING_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);

        }
    }

    private void redefineShip(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody ;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM  );

        fdef.filter.categoryBits = MyGdxGame.SHIP_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT
                | MyGdxGame.OBJECT_BIT
                | MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.GUMBA | MyGdxGame.LASER_SHIP_BIT | MyGdxGame.SHIP_GUMBA7 | MyGdxGame.SHIP_GUMBA2   ;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        timeToRedefineShip = false;
    }

    private void defineBigShip(){
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(10 / MyGdxGame.PPM, 10 / MyGdxGame.PPM  ) );
        bdef.type = BodyDef.BodyType.DynamicBody ;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM  );

        fdef.filter.categoryBits = MyGdxGame.SHIP_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT
                | MyGdxGame.OBJECT_BIT
                | MyGdxGame.SHIP_GUMBA3
                | MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.GUMBA | MyGdxGame.LASER_SHIP_BIT | MyGdxGame.SHIP_GUMBA7 | MyGdxGame.SHIP_GUMBA2 | MyGdxGame.SHIP_GUMBA4;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigShip = false;

    }
    private void defineShip() {


        BodyDef bdef = new BodyDef();
        bdef.position.set(522 / MyGdxGame.PPM ,322/ MyGdxGame.PPM );
        bdef.type = BodyDef.BodyType.DynamicBody ;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM  );

        fdef.filter.categoryBits = MyGdxGame.SHIP_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT
                | MyGdxGame.OBJECT_BIT

                | MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.GUMBA | MyGdxGame.LASER_SHIP_BIT | MyGdxGame.SHIP_GUMBA7 | MyGdxGame.ITEM_BIT
                | MyGdxGame.LASER_GUMBA_BIT | MyGdxGame.SHIP_GUMBA2;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);





    }

     public void fire(){
        laserShoot.add(new Laser(screen, b2body.getPosition().x + 11 / MyGdxGame.PPM, b2body.getPosition().y + 30 / MyGdxGame.PPM));
        laserShoot.add(new Laser(screen, b2body.getPosition().x + 11 / MyGdxGame.PPM, b2body.getPosition().y - 30 / MyGdxGame.PPM));



     }
    public void draw(Batch batch) {
        super.draw(batch);
             for(Laser ball : laserShoot)
                  ball.draw(batch);
    }
    public void hitOnHead() {
         setDestroyd = true;
    }

}
