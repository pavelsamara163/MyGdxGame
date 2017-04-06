package com.mygdx.game.Ground;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemy;

public class GroundSun extends Enemy {
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



    public GroundSun(PlayScreen screen,float x, float y) {
        super(screen, x, y);
        this.world = screen.getWorld();



        frames = new Array<TextureRegion>();
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"), i * 130, 152, 130, 76));
        walkAnimation = new Animation(0.2f, frames);


        shipStand = new TextureRegion(screen.getAtlas2().findRegion("Ship_and_Enemies"), 0,0,136,76 );


        setBounds(x, y, 86 / MyGdxGame.PPM, 86 / MyGdxGame.PPM);
        stateTimer = 0;
        setDestroyd = false;
        destroyed = false;


    }



    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() ,getY());
        bdef.type = BodyDef.BodyType.StaticBody ;
        b2body = world.createBody(bdef);

        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / MyGdxGame.PPM );

        fdef.filter.categoryBits = MyGdxGame.GUMBA;
        fdef.filter.maskBits = MyGdxGame.SHIP_BIT  ;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        stateTimer += dt;

            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));



        }

    @Override
    public void hitOnHead() {

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
            super.draw(batch);
    }

}