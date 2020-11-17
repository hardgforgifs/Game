package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.entities.Boat;

public class ChoosingUI {
    private Texture background;
    private Sprite background_sprite;
    private Texture stamina;
    private Sprite stamina_sprite;
    private Texture robustness;
    private Sprite robustness_sprite;
    private Texture acceleration;
    private Sprite acceleration_sprite;
    private Texture handling;
    private Sprite handling_sprite;
    private BitmapFont stamina_label;
    private BitmapFont robustness_label;
    private BitmapFont acceleration_label;
    private BitmapFont handling_label;
    private Boat boat1;
    private Boat boat2;
    private Boat boat3;
    private Boat boat4;
    private Sprite boat1_sprite;
    private Sprite boat2_sprite;
    private Sprite boat3_sprite;
    private Sprite boat4_sprite;
    private Boat current_boat;
    private final int boat1_x;
    private final int boat2_x;
    private final int boat3_x;
    private final int boat4_x;
    private final int boat_y;


    public ChoosingUI() {
        background = new Texture(Gdx.files.internal("Background.png"));
        background_sprite = new Sprite(background);
        background_sprite.setPosition(250,200);
        stamina = new Texture (Gdx.files.internal("Stamina_bar.png"));
        robustness = new Texture (Gdx.files.internal("Robustness_bar.png"));
        acceleration = new Texture (Gdx.files.internal("Acceleration_bar.png"));
        handling = new Texture (Gdx.files.internal("Maneuverability_bar.png"));
        stamina_sprite = new Sprite(stamina);
        robustness_sprite = new Sprite(robustness);
        acceleration_sprite = new Sprite(acceleration);
        handling_sprite = new Sprite(handling);
        stamina_label = new BitmapFont();
        robustness_label = new BitmapFont();
        acceleration_label = new BitmapFont();
        handling_label = new BitmapFont();
        stamina_sprite.setPosition(510,485);
        robustness_sprite.setPosition(510,435);
        acceleration_sprite.setPosition(510, 385);
        handling_sprite.setPosition(510, 335);
        //These will match the stats we end up settling on for the boats, so the player is given an
        //accurate representation
        boat1 = new Boat();
        boat2 = new Boat();
        boat3 = new Boat();
        boat4 = new Boat();
        //boat1_sprite = new Sprite(boat1.boatTexture);
        //boat2_sprite = new Sprite(boat1.boatTexture);
        //boat3_sprite = new Sprite(boat1.boatTexture);
        //boat4_sprite = new Sprite(boat1.boatTexture);
        boat1_sprite.scale(-0.8f);
        boat2_sprite.scale(-0.8f);
        boat3_sprite.scale(-0.8f);
        boat4_sprite.scale(-0.8f);
        boat1_x = 100;
        boat2_x = 200;
        boat3_x = 300;
        boat4_x = 400;
        boat_y = 50;

        //Set current_boat here so we have a boat to display initially
        //Replace with boat1.copy() as appropriate
        current_boat = boat1;
    }

    public void drawUI(Batch batch, float screen_width, Boat current_boat, Vector2 mousePos) {
        //Checks if the mouse is hovering over a boat and updates the bars displayed as appropriate
        if (mousePos.x > boat1_x &&
                mousePos.x < boat1_x + boat1_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat1_sprite.getHeight()) {
            //Or boat1.copy() as required
            current_boat = boat1;

        } else if (mousePos.x > boat2_x &&
                mousePos.x < boat2_x + boat2_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat2_sprite.getHeight()) {
            //Or boat2.copy() as required
            current_boat = boat2;
        } else if (mousePos.x > boat3_x &&
                mousePos.x < boat3_x + boat3_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat3_sprite.getHeight()) {
            //Or boat3.copy() as required
            current_boat = boat3;

        } else if (mousePos.x > boat4_x &&
                mousePos.x < boat4_x + boat4_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat4_sprite.getHeight()) {
            //Or boat4.copy() as required
            current_boat = boat4;
        }
        float full_bar = screen_width / 3;
        // Lines below may need some work, I don't know what the max values are
        //Or what the getter methods will be named
        stamina_sprite.setSize(full_bar * (current_boat.stamina/max_stamina), 30);
        robustness_sprite.setSize(full_bar * (current_boat.robustness/max_robustness), 30);
        acceleration_sprite.setSize(full_bar * (current_boat.acceleration/max_acceleration), 30);
        handling_sprite.setSize(full_bar * (current_boat.handling/max_handling), 30);
        //Draws components to the screen
        batch.begin();
        stamina_label.draw(batch, "Stamina:", 450, 500);
        robustness_label.draw(batch, "Robustness:", 450, 450);
        acceleration_label.draw(batch, "Acceleration:", 450, 400);
        handling_label.draw(batch, "Handling:", 450, 350);
        stamina_sprite.draw(batch);
        robustness_sprite.draw(batch);
        acceleration_sprite.draw(batch);
        handling_sprite.draw(batch);

        boat1_sprite.draw(batch);
        boat2_sprite.draw(batch);
        boat3_sprite.draw(batch);
        boat4_sprite.draw(batch);

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
                mousePos.x < boat1_x + boat1_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat1_sprite.getHeight()) {
            //Or boat1.copy() as required
            //GameData.playerBoat = boat1;

        } else if (mousePos.x > boat2_x &&
                mousePos.x < boat2_x + boat2_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat2_sprite.getHeight()) {
            //Or boat2.copy() as required
            //current_boat = boat2;
        } else if (mousePos.x > boat3_x &&
                mousePos.x < boat3_x + boat3_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat3_sprite.getHeight()) {
            //Or boat3.copy() as required
            //current_boat = boat3;

        } else if (mousePos.x > boat4_x &&
                mousePos.x < boat4_x + boat4_sprite.getWidth() &&
                mousePos.y > boat_y &&
                mousePos.y < boat_y + boat4_sprite.getHeight()) {
            //Or boat4.copy() as required
            //current_boat = boat4;
        }

    }


    public void dispose() {
        stamina_label.dispose();
        robustness_label.dispose();
        acceleration_label.dispose();
        handling_label.dispose();
        background.dispose();
        stamina.dispose();
        robustness.dispose();
        acceleration.dispose();
        handling.dispose();

    }
}
