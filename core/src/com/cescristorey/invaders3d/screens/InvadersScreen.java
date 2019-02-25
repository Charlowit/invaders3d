
package com.cescristorey.invaders3d.screens;

import com.badlogic.gdx.Screen;
import com.cescristorey.invaders3d.Invaders3D;

/** Common class for a game screen, e.g. main menu, game loop, game over screen and so on.
 * @author mzechner */
public abstract class InvadersScreen implements Screen {

	protected Invaders3D invaders;

	public InvadersScreen(Invaders3D invaders) {
		this.invaders = invaders;
	}

	/** Called when the screen should update itself, e.g. continue a simulation etc. */
	public abstract void update (float delta);

	/** Called when a screen should render itself */
	public abstract void draw (float delta);

	/** Called by GdxInvaders to check whether the screen is done.
	 * @return true when the screen is done, false otherwise */
	public abstract boolean isDone ();

	@Override
	public void render (float delta) {
		update(delta);
		draw(delta);
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
