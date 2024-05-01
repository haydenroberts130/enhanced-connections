package com.connections.view_controller;

import com.connections.model.Word;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The GameTileWord class represents a word tile in the game. It displays a word
 * and allows user interaction for selecting and deselecting the tile.
 */
public class GameTileWord extends StackPane implements Modular {
	protected static final int FILL_TRANSITION_MS = 100;
	protected static final int FILL_PULSE_TRANSITION_MS = 750;
	protected static final double PULSE_SCALE_FACTOR = 1.05;
	protected static final double PULSE_COLOR_DARK_FACTOR = 1.3;
	protected static final double PULSE_FADE_FACTOR = 0.75;
	protected static final int PULSE_REPEAT_COUNT = 4;
	private boolean selected;
	private boolean incorrect;
	private boolean styleChangeable;
	private Rectangle rectangle;
	private Text text;
	private Word word;
	private TileGridWord tileGridWord;
	private StyleStatus styleStatus;

	/**
	 * Represents the style status of the word tile.
	 */
	private enum StyleStatus {
		DEFAULT, SELECTED, INCORRECT
	}

	/*
	 * NOTE: it is >>NOT<< good that GameTileWord currently has a constructor that
	 * takes in a Font. GameTileWord needs to set its own font with the
	 * StyleManager, not the parent. This needs to be fixed later.
	 */

	/**
	 * Constructs a new GameTileWord based on another GameTileWord.
	 *
	 * @param other the GameTileWord to copy from
	 */
	public GameTileWord(GameTileWord other) {
		selected = other.selected;
		incorrect = other.incorrect;
		styleChangeable = other.styleChangeable;
		tileGridWord = other.tileGridWord;
		word = other.word;

		initAssets();
		enable();
		refreshStyle();
	}

	/**
	 * Constructs a new GameTileWord with the specified font and TileGridWord.
	 *
	 * @param font         the font to be used for the word text
	 * @param tileGridWord the TileGridWord containing this word tile
	 */
	public GameTileWord(TileGridWord tileGridWord) {
		this.word = null;
		this.tileGridWord = tileGridWord;
		this.styleChangeable = true;
		initAssets();
		enable();
	}

	/**
	 * Constructs a new GameTileWord with the specified word, font, and
	 * TileGridWord.
	 *
	 * @param word         the word associated with the word tile
	 * @param font         the font to be used for the word text
	 * @param tileGridWord the TileGridWord containing this word tile
	 */
	public GameTileWord(Word word, TileGridWord tileGridWord) {
		this(tileGridWord);
		setWord(word);
	}

	/**
	 * Sets the word associated with the word tile.
	 *
	 * @param word the word to be set
	 */
	public void setWord(Word word) {
		if (word != null) {
			this.word = word;
			text.setText(word.getText().toUpperCase());
		}
	}

	/**
	 * Sets whether the style of the word tile is changeable.
	 *
	 * @param styleChangeable true if the style is changeable, false otherwise
	 */
	public void setStyleChangeable(boolean styleChangeable) {
		this.styleChangeable = styleChangeable;
	}

	/**
	 * Sets the selected status of the word tile.
	 *
	 * @param selected true if the word tile is selected, false otherwise
	 */
	public void setSelectedStatus(boolean selected) {
		this.selected = selected;
		refreshStyle();
	}

	/**
	 * Returns the selected status of the word tile.
	 *
	 * @return true if the word tile is selected, false otherwise
	 */
	public boolean getSelectedStatus() {
		return selected;
	}

	/**
	 * Sets the incorrect status of the word tile.
	 *
	 * @param incorrect true if the word tile is marked as incorrect, false
	 *                  otherwise
	 */
	public void setIncorrectStatus(boolean incorrect) {
		this.incorrect = incorrect;
		refreshStyle();
	}

	/**
	 * Returns the incorrect status of the word tile.
	 *
	 * @return true if the word tile is marked as incorrect, false otherwise
	 */
	public boolean getIncorrectStatus() {
		return incorrect;
	}

	/**
	 * Returns the word associated with the word tile.
	 *
	 * @return the word associated with the word tile
	 */
	public Word getWord() {
		return word;
	}

	/**
	 * Initializes the assets and components of the word tile.
	 */
	private void initAssets() {
		rectangle = new Rectangle(GameTile.RECTANGLE_WIDTH, GameTile.RECTANGLE_HEIGHT);
		rectangle.setArcWidth(GameTile.CORNER_RADIUS);
		rectangle.setArcHeight(GameTile.CORNER_RADIUS);

		text = new Text();
		text.setFont(tileGridWord.getGameSessionContext().getStyleManager().getFont("franklin-normal", 700, 18));
		setWord(word);

		this.getChildren().addAll(rectangle, text);

		refreshStyle();
	}

	/**
	 * Updates the style status of the word tile based on its selected and incorrect
	 * status.
	 */
	private void updateStyleStatus() {
		if (selected && incorrect) {
			styleStatus = StyleStatus.DEFAULT;
		} else if (selected) {
			styleStatus = StyleStatus.SELECTED;
		} else if (incorrect) {
			styleStatus = StyleStatus.INCORRECT;
		} else {
			styleStatus = StyleStatus.DEFAULT;
		}
	}

	/**
	 * Returns the fill color for the rectangle based on the style status.
	 *
	 * @return the fill color for the rectangle
	 */
	private Color getRectFill() {
		StyleManager styleManager = tileGridWord.getGameSessionContext().getStyleManager();

		switch (styleStatus) {
		case SELECTED:
			return styleManager.colorSelectedRectangle();
		case INCORRECT:
			return styleManager.colorIncorrectRectangle();
		default:
		}
		return styleManager.colorDefaultRectangle();
	}

	/**
	 * Returns the fill color for the text based on the style status.
	 *
	 * @return the fill color for the text
	 */
	private Color getTextFill() {
		StyleManager styleManager = tileGridWord.getGameSessionContext().getStyleManager();

		switch (styleStatus) {
		case SELECTED:
			return styleManager.colorTextInverted();
		case INCORRECT:
			return styleManager.colorTextInverted();
		default:
		}
		return styleManager.colorText();
	}

	/**
	 * Returns the fill color for the text based on the style status.
	 *
	 * @return the fill color for the text
	 */
	private void updateStyleColor() {
		FillTransition rectangleFillTransition = new FillTransition(Duration.millis(FILL_TRANSITION_MS), rectangle);
		rectangleFillTransition.setToValue(getRectFill());
		FillTransition textFillTransition = new FillTransition(Duration.millis(FILL_TRANSITION_MS), text);
		textFillTransition.setToValue(getTextFill());
		ParallelTransition parallelTransition = new ParallelTransition(rectangleFillTransition, textFillTransition);
		parallelTransition.play();
	}

	/**
	 * Returns a sequential transition animation for the hint pulse effect.
	 *
	 * @return a sequential transition animation for the hint pulse effect
	 */
	public SequentialTransition getHintPulseAnimation() {
		StyleManager styleManager = tileGridWord.getGameSessionContext().getStyleManager();

		updateStyleStatus();

		Color answerColor = styleManager.colorDifficulty(word.getColor());
		Color answerColorDark = Color.rgb((int) (255 * answerColor.getRed() / PULSE_COLOR_DARK_FACTOR),
				(int) (255 * answerColor.getGreen() / PULSE_COLOR_DARK_FACTOR),
				(int) (255 * answerColor.getBlue() / PULSE_COLOR_DARK_FACTOR));

		// Initial Pulse

		ScaleTransition initialScale = new ScaleTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), this);
		initialScale.setFromX(1.0);
		initialScale.setFromY(1.0);
		initialScale.setToX(PULSE_SCALE_FACTOR);
		initialScale.setToY(PULSE_SCALE_FACTOR);

		FillTransition initialRectFill = new FillTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), rectangle);
		initialRectFill.setFromValue(getRectFill());
		initialRectFill.setToValue(answerColor);

		FillTransition initialTextFill = new FillTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), text);
		initialTextFill.setFromValue(getTextFill());
		initialTextFill.setToValue(styleManager.colorTextNeutral());

		ParallelTransition initialParallel = new ParallelTransition(initialScale, initialRectFill, initialTextFill);

		// Repeated Pulse by Using the Answer Color Only

		int cycleCount = PULSE_REPEAT_COUNT * 2;

		ScaleTransition continueScale = new ScaleTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), this);
		continueScale.setToX(1.0);
		continueScale.setToY(1.0);

		FadeTransition continueFade = new FadeTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), rectangle);
		continueFade.setToValue(PULSE_FADE_FACTOR);

		FillTransition continueRectFill = new FillTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), rectangle);
		continueRectFill.setToValue(answerColorDark);

		FillTransition continueTextFill = new FillTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), text);
		continueTextFill.setToValue(styleManager.colorTextInverted());

		ParallelTransition continueParallel = new ParallelTransition(continueScale, continueFade, continueRectFill,
				continueTextFill);
		continueParallel.setAutoReverse(true);
		continueParallel.setCycleCount(cycleCount);

		SequentialTransition sequence = new SequentialTransition(initialParallel, continueParallel);

		return sequence;
	}

	/**
	 * Returns a sequential transition animation for the hint pulse effect.
	 *
	 * @return a sequential transition animation for the hint pulse effect
	 */
	public ParallelTransition getHintReturnNormalAnimation() {
		updateStyleStatus();

		ScaleTransition continueScale = new ScaleTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), this);
		continueScale.setToX(1.0);
		continueScale.setToY(1.0);

		FadeTransition continueFade = new FadeTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), rectangle);
		continueFade.setToValue(1.0);

		FillTransition continueRectFill = new FillTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), rectangle);
		continueRectFill.setToValue(getRectFill());

		FillTransition continueTextFill = new FillTransition(Duration.millis(FILL_PULSE_TRANSITION_MS), text);
		continueTextFill.setToValue(getTextFill());

		ParallelTransition continueParallel = new ParallelTransition(continueScale, continueFade, continueRectFill,
				continueTextFill);

		return continueParallel;
	}

	/**
	 * Disables the word tile and removes its event handlers.
	 */
	public void disable() {
		this.setDisable(true);
		this.setOnMouseClicked(null);
		this.setOnMouseEntered(null);
		this.setOnMouseExited(null);
	}

	/**
	 * Enables the word tile and sets its event handlers for user interaction.
	 */
	public void enable() {
		this.setDisable(false);
		this.setOnMouseClicked(event -> {
			if (!selected && tileGridWord.getSelectedTileWordCount() < TileGridWord.MAX_SELECTED) {
				setSelectedStatus(true);
				tileGridWord.incrementSelectedTileWordCount();
			} else if (selected) {
				setSelectedStatus(false);
				tileGridWord.decrementSelectedTileWordCount();
			}
		});

		this.setOnMouseEntered(event -> {
			this.setCursor(Cursor.HAND);
		});

		this.setOnMouseExited(event -> {
			this.setCursor(Cursor.DEFAULT);
		});
	}

	/**
	 * Adds a fade-in transition for the word text to the provided parallel
	 * transition.
	 *
	 * @param fadeInTransition the parallel transition to add the fade-in transition
	 *                         to
	 */
	public void fadeInWordText(ParallelTransition fadeInTransition) {
		text.setOpacity(0);

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), text);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeInTransition.getChildren().add(fadeTransition);
	}

	/**
	 * Refreshes the style of the word tile based on its style status.
	 */
	@Override
	public void refreshStyle() {
		if (styleChangeable) {
			updateStyleStatus();
			updateStyleColor();
		}
	}

	/**
	 * Returns the GameSessionContext associated with the word tile.
	 *
	 * @return the GameSessionContext associated with the word tile
	 */
	@Override
	public GameSessionContext getGameSessionContext() {
		return tileGridWord.getGameSessionContext();
	}
}
