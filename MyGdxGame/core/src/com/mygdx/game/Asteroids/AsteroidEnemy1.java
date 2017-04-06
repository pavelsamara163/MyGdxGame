package com.mygdx.game.Asteroids;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemy;

public  class AsteroidEnemy1 extends Enemy {
    public enum State{RUNNING,EXPLOSION,CLOSE}
    private State currentState;
    private State previousState;
    Body b2body;

    private   boolean setDestroyd;
    private  boolean destroyed;

    private Array<TextureRegion> frames;
    private float stateTimer;
    public Animation walkAnimation;
    public Animation destroydEnemy;
    public TextureRegion shipStand;


    public AsteroidEnemy1 (PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.world = screen.getWorld();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(screen.getAtlas4().findRegion("rock1"), i * 66, 0, 64, 64));
        walkAnimation = new Animation(0.2f, frames);
        frames.clear();

        for (int e = 0; e < 63; e++)
            frames.add(new TextureRegion(screen.getAtlas3().findRegion("Exp_type_A"), e * 119, 0, 119, 119));
        destroydEnemy = new Animation(0.050f, frames);
        frames.clear();

        shipStand = new TextureRegion(screen.getAtlas4().findRegion("rock1") ,64,0,64,64 );
        setBounds(x, y, 64 / MyGdxGame.PPM , 64 / MyGdxGame.PPM );
        stateTimer = 0;
        setDestroyd = false;
        destroyed = false;




    }



    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() ,getY());
        bdef.type = BodyDef.BodyType.DynamicBody ;
        b2body = world.createBody(bdef);

        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MyGdxGame.PPM );

        fdef.filter.categoryBits = MyGdxGame.ENEMY_HEAD_BIT;
        fdef.filter.maskBits = MyGdxGame.SHIP_BIT | MyGdxGame.LASER_SHIP_BIT ;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        stateTimer += dt;
        if (setDestroyd && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            Hud.addScore(100);
        } else if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));
            b2body.setLinearVelocity(velocityAster);
        } else if (!setDestroyd || destroyed) {
            setRegion(getFrame(dt));

        }
    }

    @Override
    public void hitOnHead() {
        setDestroyd = true;
    }


    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region ;
        switch (currentState) {
            case RUNNING:
                region = walkAnimation.getKeyFrame(stateTimer, true);
                break;
            case EXPLOSION:
                region = destroydEnemy.getKeyFrame(stateTimer, true);
                break;
            default :
                region = shipStand;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 1;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if (setDestroyd && !destroyed)
            return State.RUNNING;
        else if (!destroyed)
            return State.RUNNING;
        else if (setDestroyd)
            return State.EXPLOSION;
        else
            return State.CLOSE;
    }
    public void draw(Batch batch){
        if(!destroyed || stateTimer < 3)

            super.draw(batch);
    }

}