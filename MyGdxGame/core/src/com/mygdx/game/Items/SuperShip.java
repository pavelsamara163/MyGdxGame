package com.mygdx.game.Items;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Ship;


public class SuperShip  extends Item{
        private float stateTimer;
    public SuperShip(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas10().findRegion("PirateFighters"), 10, 1530, 480, 460);
        velocity = new Vector2(0,-0.2f);


    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() ,getY());
        bdef.type = BodyDef.BodyType.DynamicBody ;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(11 / MyGdxGame.PPM );

        fdef.filter.categoryBits = MyGdxGame.ITEM_BIT;
        fdef.filter.maskBits = MyGdxGame.SHIP_BIT ;


        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Ship ship) {
        ship.grow();

    }


    public void hitOnHead() {
        toDestroy = true;
    }


    @Override
    public void update( float dt){
        super.update(dt);
          setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            body.setLinearVelocity(velocity);

    }


}
