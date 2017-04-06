package com.mygdx.game.Other;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class LaserGumba7 extends Sprite {
    private World world;
    private Body b2body;
    private boolean fireRight;
    private PlayScreen screen;
    private Animation fireAnimation;
    private float stateTime;
    public    boolean dest;
    private boolean setToDestroy;
    private Array<TextureRegion> frames;
    Texture rockAnimat ;



    public LaserGumba7(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();


        frames = new Array<TextureRegion>();{
            for (int i = 0; i < 22; i++)
                frames.add(new TextureRegion(screen.getAtlas6().findRegion("redLaserRay"), 2, 2, 146, 64));

        }
        fireAnimation = new Animation(0.1f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 64 / MyGdxGame.PPM, 64 / MyGdxGame.PPM);
        defineLaserGumba();


    }

    public void defineLaserGumba() {


        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);



        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / MyGdxGame.PPM );

        fdef.filter.categoryBits = MyGdxGame.LASER_GUMBA_BIT;
        fdef.filter.maskBits =  MyGdxGame.OBJECT_BIT  | MyGdxGame.SHIP_BIT ;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(-5, 0));


    }

    public void update(float dt) {

        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(fireAnimation.getKeyFrame(stateTime, true));


        if ((stateTime > 2 || setToDestroy) && !dest) {
            dest = true;
            world.destroyBody(b2body);
        }
    }

    public void setToDestroy(){
        setToDestroy = true;

    }

    public boolean isDestroyed(){
        return dest;
    }
}

