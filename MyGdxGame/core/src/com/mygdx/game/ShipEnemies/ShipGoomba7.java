package com.mygdx.game.ShipEnemies;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Other.LaserGumba7;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public  class ShipGoomba7 extends Sprite {
    World world;
    Body b2body;


    private enum State{RUNNING,EXPLOSION,CLOSE}
    private State currentState;
    private State previousState;



    private   boolean setDestroyd;
    private  boolean destroyed;

    private Array<TextureRegion> frames;
    private float stateTimer;
    public Animation walkAnimation;
    public Animation destroydEnemy;
    public TextureRegion shipStand;
    private Array<LaserGumba7> laserShoot1;
    private PlayScreen screen ;
    private Vector2 velocity;
    long lastDropTime ;


    public ShipGoomba7(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        velocity = new Vector2(-0.5f,0);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(screen.getAtlas1().findRegion("strip_scythe"),  0, i *128, 128, 128));
        walkAnimation = new Animation(0.2f, frames);


        for (int e = 0; e < 63; e++)
            frames.add(new TextureRegion(screen.getAtlas3().findRegion("Exp_type_B"), e * 63, 0, 58, 58));
        destroydEnemy = new Animation(0.050f, frames);

       /* for (int e = 0; e < 63; e++)
            frames.add(new TextureRegion(screen.getAtlas9().findRegion("12"), e * 62, 0, 64, 64));
        destroydEnemy = new Animation(0.050f, frames);*/

       /* for (int e = 0; e < 16; e++)
            frames.add(new TextureRegion(screen.getAtlas5().findRegion("Explousing"), e * 190, 0, 140, 140));
        destroydEnemy = new Animation(0.1f, frames);*/

        shipStand = new TextureRegion(screen.getAtlas1().findRegion("strip_scythe") ,0,128,128,128 );
        setBounds(x, y, 111 / MyGdxGame.PPM , 111 / MyGdxGame.PPM );
        setRegion(shipStand);
        setDestroyd = false;
        destroyed = false;
        defineEnemy();
        laserShoot1 = new Array<LaserGumba7>();


    }

    public void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() , getY() );
        bdef.type = BodyDef.BodyType.DynamicBody ;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.SHIP_GUMBA7;
        fdef.filter.maskBits = MyGdxGame.SHIP_BIT
                | MyGdxGame.LASER_SHIP_BIT  ;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }



    public void update(float dt) {
        stateTimer += dt;

        if (setDestroyd && !destroyed ) {
            world.destroyBody(b2body);
            destroyed = true;
            Hud.addScore(300);
        } else if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));
            b2body.setLinearVelocity(velocity);

        }
        else if  (!setDestroyd ||destroyed) {
            setRegion(getFrame(dt));
        }

        for(LaserGumba7 ball : laserShoot1) {
            ball.update(dt);
            if (ball.isDestroyed())
                laserShoot1.removeValue(ball, true);

        }
    }
    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case RUNNING:
                region = walkAnimation.getKeyFrame(stateTimer,true);
                break;
            case EXPLOSION:
                region = destroydEnemy.getKeyFrame(stateTimer,true);
                break;
            default:
                region = shipStand;

        }
        stateTimer = currentState == previousState ? stateTimer + dt : 1;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if (!setDestroyd && !destroyed)
            return State.RUNNING;
        else if (!destroyed)
            return State.RUNNING;
        else if (setDestroyd)
            return State.EXPLOSION;
        else
            return State.CLOSE;
    }

    public void hitOnHead() {
        setDestroyd = true;

    }

    public void fire1(){
        laserShoot1.add(new LaserGumba7(screen,b2body.getPosition().x +0 / MyGdxGame.PPM ,b2body.getPosition().y +0 / MyGdxGame.PPM ));
        lastDropTime = TimeUtils.nanoTime();



    }
    public void draw(Batch batch) {
        if (!destroyed || stateTimer < 2.5)
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000) fire1();
            else if (!setDestroyd || destroyed )
                super.draw(batch);

        for (LaserGumba7 ball : laserShoot1)
            ball.draw(batch);


    }

}