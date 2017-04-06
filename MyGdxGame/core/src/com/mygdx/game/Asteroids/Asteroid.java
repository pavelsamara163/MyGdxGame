package com.mygdx.game.Asteroids;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Ship;
import com.mygdx.game.Tools.interectivTileObject;

public class Asteroid extends interectivTileObject {
    public Asteroid(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.ASTEROID_BIT );
    }

    @Override
    public void onHeadHit(Ship ship) {
        Gdx.app.log("Aster", "callision");
        setCategoryFilter(MyGdxGame.DESTROIT_BIT);
          getCell().setTile(null);
            Hud.addScore(200);


    }


}
