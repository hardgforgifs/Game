package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ChoosingUI extends UI{
    private Texture background;
    private Sprite background_sprite;
    private Texture bar;
    private Sprite bar_sprite;
    private Texture boat;
    private Sprite boat_sprite;
    private BitmapFont label;
    private Texture boat_texture;
    private final int boat1_x;
    private final int boat2_x;
    private final int boat3_x;
    private final int boat4_x;
    private final int boat_y;
    private final int BOAT_WIDTH;
    private final int BOAT_HEIGHT;
    private int[] boats_stamina;
    private int[] boats_robustness;
    private int[] boats_acceleration;
    private int[] boats_handling;
    private int[] current_stats;


    public ChoosingUI() {
        background = new Texture(Gdx.files.internal("Background.png"));
        background_sprite = new Sprite(background);
        background_sprite.setPosition(250,200);
        boat1_x = 100;
        boat2_x = 200;
        boat3_x = 300;
        boat4_x = 400;
        boat_y = 50;
        BOAT_WIDTH = 50;
        BOAT_HEIGHT = 100;
        boat = new Texture("Boat1.png");
    }


    public void drawUI(Batch batch, float screen_width, Vector2 mousePos) {
        //Checks if the mouse is hovering over a boat and updates the bars displayed as appropriate
        //Assuming boats are 50 wide and 100 long
        if (mousePos.x > boat1_x &&
                mousePos.x < boat1_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + BOAT_HEIGHT) {
            current_stats[0] = boats_stamina[0];
            current_stats[1] = boats_robustness[0];
            current_stats[2] = boats_acceleration[0];
            current_stats[3] = boats_handling[0];
        } else if (mousePos.x > boat2_x &&
                mousePos.x < boat2_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + BOAT_HEIGHT) {
            current_stats[0] = boats_stamina[1];
            current_stats[1] = boats_robustness[1];
            current_stats[2] = boats_acceleration[1];
            current_stats[3] = boats_handling[1];
        } else if (mousePos.x > boat3_x &&
                mousePos.x < boat3_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + + BOAT_HEIGHT) {
            current_stats[0] = boats_stamina[2];
            current_stats[1] = boats_robustness[2];
            current_stats[2] = boats_acceleration[2];
            current_stats[3] = boats_handling[2];
        } else if (mousePos.x > boat4_x &&
                mousePos.x < boat4_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + BOAT_HEIGHT) {
            current_stats[0] = boats_stamina[3];
            current_stats[1] = boats_robustness[3];
            current_stats[2] = boats_acceleration[3];
            current_stats[3] = boats_handling[3];
        }

        float full_bar = screen_width / 3;
        batch.begin();

        bar_sprite.setSize(full_bar * (current_stats[0]/200), 30);
        bar_sprite.setPosition(510,485);
        bar = new Texture("Stamina_bar.png");
        bar_sprite.setTexture(bar);
        bar_sprite.draw(batch);

        bar_sprite.setSize(full_bar * (current_stats[1]/200), 30);
        bar_sprite.setPosition(510,435);
        bar = new Texture("Robustness_bar.png");
        bar_sprite.setTexture(bar);
        bar_sprite.draw(batch);

        bar_sprite.setSize(full_bar * (current_stats[2]/200), 30);
        bar_sprite.setPosition(510,385);
        bar = new Texture("Acceleration_bar.png");
        bar_sprite.setTexture(bar);
        bar_sprite.draw(batch);

        bar_sprite.setSize(full_bar * (current_stats[3]/200), 30);
        bar_sprite.setPosition(510,335);
        bar = new Texture("Maneuverability_bar.png");
        bar_sprite.setTexture(bar);
        bar_sprite.draw(batch);

        label.draw(batch, "Stamina:", 450, 500);
        label.draw(batch, "Robustness:", 450, 450);
        label.draw(batch, "Acceleration:", 450, 400);
        label.draw(batch, "Handling:", 450, 350);

        boat_sprite.setPosition(boat1_x, boat_y);
        boat_sprite.draw(batch);
        boat_sprite.setPosition(boat2_x, boat_y);
        boat_sprite.draw(batch);
        boat_sprite.setPosition(boat3_x, boat_y);
        boat_sprite.draw(batch);
        boat_sprite.setPosition(boat4_x, boat_y);
        boat_sprite.draw(batch);

        batch.end();

        //Keeps music on repeat
        //Dont have the GameData class on my end, so remove comments below
        /*if (GameData.music.isPlaying() == false) {
            GameData.music.play();
        }*/
    }

    public void getInput(Vector2 mousePos) {
        //Checks if the mouse is hovering over a boat and updates the bars displayed as appropriate
        if (mousePos.x > boat1_x &&
                mousePos.x < boat1_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y +BOAT_HEIGHT) {
            GameData.stamina = boats_stamina[0];
            GameData.robustness = boats_robustness[0];
            GameData.acceleration = boats_acceleration[0];
            GameData.handling = boats_handling[0];

        } else if (mousePos.x > boat2_x &&
                mousePos.x < boat2_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + BOAT_HEIGHT) {
            GameData.stamina = boats_stamina[1];
            GameData.robustness = boats_robustness[1];
            GameData.acceleration = boats_acceleration[1];
            GameData.handling = boats_handling[1];
        } else if (mousePos.x > boat3_x &&
                mousePos.x < boat3_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + BOAT_HEIGHT) {
            GameData.stamina = boats_stamina[2];
            GameData.robustness = boats_robustness[2];
            GameData.acceleration = boats_acceleration[2];
            GameData.handling = boats_handling[2];
        } else if (mousePos.x > boat4_x &&
                mousePos.x < boat4_x + BOAT_WIDTH &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + BOAT_HEIGHT) {
            GameData.stamina = boats_stamina[3];
            GameData.robustness = boats_robustness[3];
            GameData.acceleration = boats_acceleration[3];
            GameData.handling = boats_handling[3];
        }

    }


    public void dispose() {
        label.dispose();
        boat.dispose();
        background.dispose();
        bar.dispose();
        background.dispose();
    }
}
