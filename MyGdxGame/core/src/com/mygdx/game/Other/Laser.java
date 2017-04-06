package com.mygdx.game.Other;

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

public class Laser extends Sprite {
   private World world;
    private Body b2body;
   private boolean fireRight;
    private PlayScreen screen;
   private Animation fireAnimation;
    private float stateTime;
   protected  boolean dest;
    private boolean setToDestroy;
    private Array<TextureRegion> frames;


    public Laser(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.fireRight = fireRight ;

        frames = new Array<TextureRegion>();{
        for (int i = 0; i < 19; i++)
            frames.add(new TextureRegion(screen.getAtlas1().findRegion("thrust_green"), 0, i * 128, 132, 124));
    }
        fireAnimation = new Animation(0.1f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x  ,y , 64 / MyGdxGame.PPM  , 64 / MyGdxGame.PPM  );
        defineLaser();

    }

    public void defineLaser() {


        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);



        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM );

        fdef.filter.categoryBits = MyGdxGame.LASER_SHIP_BIT;
        fdef.filter.maskBits = MyGdxGame.GUMBA | MyGdxGame.OBJECT_BIT  | MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.SHIP_GUMBA1 | MyGdxGame.SHIP_GUMBA2
        | MyGdxGame.SHIP_GUMBA3 | MyGdxGame.SHIP_GUMBA4 | MyGdxGame.SHIP_GUMBA5 | MyGdxGame.SHIP_BOSS | MyGdxGame.ENEMY_BIT | MyGdxGame.ITEM_BIT   ;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(7,0));

    }

    public void update(float dt) {

        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
        setRegion(fireAnimation.getKeyFrame(stateTime, true));


        if ((stateTime > 0.65 || setToDestroy) && !dest) {
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
