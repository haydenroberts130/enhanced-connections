package com.connections.view_controller;

import com.connections.model.DifficultyColor;
import com.connections.web.WebUser;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * The TileGridAchievement class represents a grid of achievement tiles. It
 * extends the Pane class and implements the Modular interface.
 */
public class TileGridAchievement extends Pane implements Modular {

	private GameSessionContext gameSessionContext;
	private GridPane gridPane;

	/**
	 * Constructs a new TileGridAchievement object.
	 *
	 * @param gameSessionContext The GameSessionContext object for accessing shared
	 *                           resources.
	 */
	public TileGridAchievement(GameSessionContext gameSessionContext) {
		this.gameSessionContext = gameSessionContext;
		initAssets();
		initSampleAchievements();
		refreshStyle();
	}

	/**
	 * Initializes the assets for the TileGridAchievement.
	 */
	private void initAssets() {
		gridPane = new GridPane();
		gridPane.setHgap(TileGridWord.GAP);
		gridPane.setVgap(TileGridWord.GAP);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setMaxSize(TileGridWord.PANE_WIDTH, TileGridWord.PANE_HEIGHT);
		setMaxSize(TileGridWord.PANE_WIDTH, TileGridWord.PANE_HEIGHT);
		getChildren().add(gridPane);
	}

	/**
	 * Initializes sample achievements in the grid.
	 */
	private void initSampleAchievements() {
		String[] achievementLabels = { "1 standard game completed", "10 standard games completed",
				"50 standard games completed", "100 standard games completed", "1 time trial game completed",
				"10 time trial games completed", "50 time trial games completed", "100 time trial games completed",
				"1 puzzle completed with no mistakes", "10 puzzles completed with no mistakes",
				"50 puzzles completed with no mistakes", "100 puzzles completed with no mistakes",
				"1 time trial puzzle completed in under 30 seconds",
				"10 time trial puzzles completed in under 30 seconds",
				"50 time trial puzzles completed in under 30 seconds",
				"100 time trial puzzles completed in under 30 seconds" };

		DifficultyColor[] colorIntensity = { DifficultyColor.YELLOW, DifficultyColor.GREEN, DifficultyColor.BLUE,
				DifficultyColor.PURPLE };

		gridPane.getChildren().clear();

		WebUser user = gameSessionContext.getWebSessionContext().getSession().getUser();

		for (int row = 0; row < TileGridWord.ROWS; row++) {
			for (int col = 0; col < TileGridWord.COLS; col++) {
				DifficultyColor color = colorIntensity[col];
				gridPane.add(new GameTileAchievement(color, achievementLabels[row * TileGridWord.COLS + col], user,
						this, row), col, row);
			}
		}
	}

	/**
	 * Animates the completion of achievements in the grid.
	 */
	public void animateCompletion() {
		for (Node node : gridPane.getChildren()) {
			if (node instanceof GameTileAchievement) {
				GameTileAchievement tileAchievement = (GameTileAchievement) node;
				tileAchievement.animateCompletion();
			}
		}
	}

	/**
	 * Refreshes the style of the TileGridAchievement and its children based on the
	 * current style settings.
	 */
	@Override
	public void refreshStyle() {
		setBackground(new Background(
				new BackgroundFill(gameSessionContext.getStyleManager().colorWholeAchievementsPane(), null, null)));

		for (Node node : gridPane.getChildren()) {
			if (node instanceof Modular) {
				Modular stylableNode = (Modular) node;
				stylableNode.refreshStyle();
			}
		}
	}

	/**
	 * Returns the GameSessionContext object associated with this
	 * TileGridAchievement.
	 *
	 * @return The GameSessionContext object.
	 */
	@Override
	public GameSessionContext getGameSessionContext() {
		return gameSessionContext;
	}
}