package com.mygdx.game.Sprites;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Items.ItemDef;
import com.mygdx.game.Items.SuperShip;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class bonusItem extends Enemy  {
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


    public bonusItem(PlayScreen screen, float x, float y) {
        super(screen, x, y );
        this.screen = screen;


        frames = new Array<TextureRegion>();
        for (int i = 0; i < 16; i++)
            frames.add(new TextureRegion(screen.getAtlas10().findRegion("1-6"),  304, 10, 88, 88));
        walkAnimation = new Animation(0.2f, frames);


        for (int e = 0; e < 16; e++)
            frames.add(new TextureRegion(screen.getAtlas9().findRegion("4"), e * 128, 6, 56, 64));
        destroydEnemy = new Animation(0.1f, frames);


        shipStand = new TextureRegion(screen.getAtlas10().findRegion("1-6") ,64,0,64,64 );
        setBounds(x, y, 64 / MyGdxGame.PPM, 64 / MyGdxGame.PPM);
        setRegion(shipStand);
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



        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.SHIP_BIT | MyGdxGame.LASER_SHIP_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        stateTimer += dt;

        if (setDestroyd && !destroyed ) {
            world.destroyBody(b2body);
            destroyed = true;
            Hud.addScore(15000);
            screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x, b2body.getPosition().y - 16 / MyGdxGame.PPM),
                    SuperShip.class));


        }
        else if (!destroyed)  {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));
            b2body.setLinearVelocity(velocityShip);
            b2body.setLinearVelocity(velocityBonus);
        }
        else if  (!setDestroyd || destroyed) {
            setRegion(getFrame(dt));
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
    @Override
    public void hitOnHead() {
        setDestroyd = true;

    }

}