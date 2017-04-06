package com.mygdx.game.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Items.ItemDef;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.interectivTileObject;

public class Coin extends interectivTileObject {
    private static TiledMapTileSet tileSet ;
    private final int BLANK_COIN = 0;
    public Coin(PlayScreen screen , MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("planet_Pack") ;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.COIN_BIT);
    }

    @Override
    public void onHeadHit(Ship ship) {
        Gdx.app.log("Coin", "calliscon");
            if(getCell().getTile().getId() == BLANK_COIN)
          //  MyGdxGame.manager.get("audio/sounds/bump.wav", Sound.class).play();

            if (object.getProperties().containsKey("planet_Pack")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / MyGdxGame.PPM),
                        MyGdxGame.class));
              //  MyGdxGame.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();

              //  MyGdxGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
    }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(500);
    }
}
