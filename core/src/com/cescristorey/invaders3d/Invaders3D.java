package com.cescristorey.invaders3d;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.cescristorey.invaders3d.screens.GameLoop;
import com.cescristorey.invaders3d.screens.GameOver;
import com.cescristorey.invaders3d.screens.InvadersScreen;
import com.cescristorey.invaders3d.screens.MainMenu;

public class Invaders3D extends Game {

	private Music music;
	private FPSLogger fps;

	private Controller controller;
	private ControllerAdapter controllerListener = new ControllerAdapter(){
		@Override
		public void connected(Controller c) {
			if (controller == null) {
				controller = c;
			}
		}
		@Override
		public void disconnected(Controller c) {
			if (controller == c) {
				controller = null;
			}
		}
	};

	public Controller getController() {
		return controller;
	}

	@Override
	public void render () {
		InvadersScreen currentScreen = getScreen();

		// update the screen
		currentScreen.render(Gdx.graphics.getDeltaTime());

		// When the screen is done we change to the
		// next screen. Ideally the screen transitions are handled
		// in the screen itself or in a proper state machine.
		if (currentScreen.isDone()) {
			// dispose the resources of the current screen
			currentScreen.dispose();

			// if the current screen is a main menu screen we switch to
			// the game loop
			if (currentScreen instanceof MainMenu) {
				setScreen(new GameLoop(this));
			} else {
				// if the current screen is a game loop screen we switch to the
				// game over screen
				if (currentScreen instanceof GameLoop) {
					setScreen(new GameOver(this));
				} else if (currentScreen instanceof GameOver) {
					// if the current screen is a game over screen we switch to the
					// main menu screen
					setScreen(new MainMenu(this));
				}
			}
		}

		// fps.log();
	}

	@Override
	public void create () {
		Array<Controller> controllers = Controllers.getControllers();
		if (controllers.size > 0) {
			controller = controllers.first();
		}
		Controllers.addListener(controllerListener);

		setScreen(new MainMenu(this));
		music = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/8.12.mp3", Files.FileType.Internal));
		music.setLooping(true);
		music.play();
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp (int keycode) {
				if (keycode == Input.Keys.ENTER && Gdx.app.getType() == Application.ApplicationType.WebGL) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				}
				return true;
			}
		});

		fps = new FPSLogger();
	}

	/** For this game each of our screens is an instance of InvadersScreen.
	 * @return the currently active {@link InvadersScreen}. */
	@Override
	public InvadersScreen getScreen () {
		return (InvadersScreen)super.getScreen();
	}
}
