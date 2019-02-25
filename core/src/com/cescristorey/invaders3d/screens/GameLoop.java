package com.cescristorey.invaders3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.cescristorey.invaders3d.Invaders3D;
import com.cescristorey.invaders3d.Renderer;
import com.cescristorey.invaders3d.simulation.Simulation;
import com.cescristorey.invaders3d.simulation.SimulationListener;

public class GameLoop extends InvadersScreen implements SimulationListener {
	/** the simulation **/
	private final Simulation simulation;
	/** the renderer **/
	private final Renderer renderer;
	/** explosion sound **/
	private final Sound explosion;
	/** shot sound **/
	private final Sound shot;

	/** controller **/
	private int buttonsPressed = 0;
	private ControllerListener listener = new ControllerAdapter() {
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			buttonsPressed++;
			return true;
		}

		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			buttonsPressed--;
			return true;
		}
	};

	public GameLoop (Invaders3D invaders) {
		super(invaders);
		simulation = new Simulation();
		simulation.listener = this;
		renderer = new Renderer();
		explosion = Gdx.audio.newSound(Gdx.files.internal("data/explosion.wav"));
		shot = Gdx.audio.newSound(Gdx.files.internal("data/shot.wav"));

		if (invaders.getController() != null) {
			invaders.getController().addListener(listener);
		}
	}

	@Override
	public void dispose () {
		renderer.dispose();
		shot.dispose();
		explosion.dispose();
		if (invaders.getController() != null) {
			invaders.getController().removeListener(listener);
		}
		simulation.dispose();
	}

	@Override
	public boolean isDone () {
		return simulation.ship.lives == 0;
	}

	@Override
	public void draw (float delta) {
		renderer.render(simulation, delta);
	}

	@Override
	public void update (float delta) {
		simulation.update(delta);

		float accelerometerY = Gdx.input.getAccelerometerY();
		if (accelerometerY < 0)
			simulation.moveShipLeft(delta, Math.abs(accelerometerY) / 10);
		else
			simulation.moveShipRight(delta, Math.abs(accelerometerY) / 10);

		if (invaders.getController() != null) {
			if (buttonsPressed > 0) {
				simulation.shot();
			}

			// if the left stick moved, move the ship
			float axisValue = invaders.getController().getAxis(Ouya.AXIS_LEFT_X) * 0.5f;
			if (Math.abs(axisValue) > 0.25f) {
				if (axisValue > 0) {
					simulation.moveShipRight(delta, axisValue);
				} else {
					simulation.moveShipLeft(delta, -axisValue);
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT) || Gdx.input.isKeyPressed(Keys.A)) simulation.moveShipLeft(delta, 0.5f);
		if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) || Gdx.input.isKeyPressed(Keys.D)) simulation.moveShipRight(delta, 0.5f);
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) simulation.shot();
	}

	@Override
	public void explosion () {
		explosion.play();
	}

	@Override
	public void shot () {
		shot.play();
	}
}
