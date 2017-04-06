package com.mygdx.game.Animations;


/*public class RockAnimations {

    public Rectangle rocksren;
    private static final int FRAME_COLS_ROCK = 16;
    private static final int FRAME_ROWS_ROCK = 1;
    Animation rockAnimation;
    Texture rockAnimat0;
    TextureRegion[] rockFrames;
    float stateTimes;
    Vector2 rockLocation = new Vector2(0, 0);//ПЕременная астеров

    public RockAnimations(int x, int y) {
        rocksren  = new Rectangle();
        rocksren = new Rectangle();
         rocksren.x = 800 /2 - 64/2;
        rocksren.y = 20;
        rocksren.height = 64;
        rocksren.width =64;

        rockAnimat0 = new Texture("rock0Animation.png");
        rockLocation = new Vector2(20, (MyGdxGame.screenHidth / 2) - (rockAnimat0.getHeight()));
        TextureRegion[][] tmps = TextureRegion.split(rockAnimat0, rockAnimat0.getWidth() / FRAME_COLS_ROCK, rockAnimat0.getHeight() / FRAME_ROWS_ROCK);
        rockFrames = new TextureRegion[FRAME_COLS_ROCK * FRAME_ROWS_ROCK];
        int rocks = 0;
        for (int r = 0; r < FRAME_ROWS_ROCK; r++) {
            for (int k = 0; k < FRAME_COLS_ROCK; k++) {
                rockFrames[rocks++] = tmps[r][k];
            }
        }
        rockAnimation = new Animation(0.350f, rockFrames);
        stateTimes = 0f;

    }


    public Animation getRockAnimation() {
        return rockAnimation;
    }

    public Rectangle getRocksren() {
        return rocksren;
    }
}
*/