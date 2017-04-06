package com.mygdx.game.Sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    public World world;
    public PlayScreen screen;
    public Body b2body;
    public Body body;
      public Vector2 velocityAster;
      public  Vector2 velocityShip;
      public  Vector2 velocityBonus;
        protected boolean destroyed;


    public Enemy (PlayScreen screen,float x, float y ){
        this.world = screen.getWorld();
        this.screen = screen ;
        setPosition(x, y);
        defineEnemy();
        velocityAster = new  Vector2 (-1,0);
        velocityShip = new  Vector2 (0,0);
        velocityBonus = new  Vector2 (-2,0);

    }




    public abstract  void defineEnemy() ;

    public abstract void update(float dt);

    public abstract void hitOnHead();





    public boolean isDestroyed() {
        return destroyed;
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocityAster.x = -velocityAster.x;
            velocityShip.x = -velocityShip.x ;

        if (y)
            velocityAster.y = -velocityAster.y ;
            velocityShip.y = - velocityShip.y ;
    }

}
