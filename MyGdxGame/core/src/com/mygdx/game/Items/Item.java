package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Ship;


public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen,float x,float y){
        this.screen = screen;
        this.world = screen.getWorld();

        toDestroy = false;
        destroyed = false;

        setPosition(x,y);
        setBounds(getX(),getY(),64 / MyGdxGame.PPM  , 64 / MyGdxGame.PPM  );
        defineItem();
    }
    public abstract void defineItem();
    public abstract void use( Ship ship );

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }


    }

    public void draw (Batch batch){
        if(!destroyed)
            super.draw(batch);
    }
    public void destroy(){
        toDestroy = true;
    }


}
