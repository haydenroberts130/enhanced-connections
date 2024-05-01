package com.connections.view_controller;

import javafx.scene.shape.SVGPath;

/**
 * The HintMenuButton class represents a button with a hint icon in the menu. It
 * extends the SVGButton class and implements the Modular interface.
 */
public class HintMenuButton extends SVGButton implements Modular {

	/**
	 * Constructs a new HintMenuButton with the specified GameSessionContext.
	 *
	 * @param gameSessionContext the GameSessionContext used by the hint menu button
	 */
	public HintMenuButton(GameSessionContext gameSessionContext) {
		super(gameSessionContext);

		SVGPath hintSVG = new SVGPath();
		hintSVG.setContent(
				"M15,19H9c-0.6,0-1-0.4-1-1v-0.5c0-1.4-0.6-2.8-1.7-3.9C4.7,12,3.9,9.9,4,7.7C4.2,3.5,7.7,0.1,11.9,0L12,0 c4.4,0,8,3.6,8,8c0,2.1-0.8,4.2-2.4,5.7c-1.1,1-1.6,2.4-1.6,3.8V18C16,18.6,15.6,19,15,19z M10,17h4c0.1-1.8,0.9-3.4,2.2-4.8 C17.4,11.1,18,9.6,18,8c0-3.3-2.7-6-6-6l-0.1,0C8.8,2.1,6.1,4.6,6,7.8C5.9,9.4,6.6,11,7.7,12.2C9.1,13.6,9.9,15.3,10,17z M12,24L12,24c-2.2,0-4-1.8-4-4v-2c0-0.6,0.4-1,1-1h6c0.6,0,1,0.4,1,1v2C16,22.2,14.2,24,12,24z M10,19v1c0,1.1,0.9,2,2,2 H12c1.1,0,2-0.9,2-2v-1H10z M9,9C8.4,9,8,8.6,8,8c0-2.2,1.8-4,4-4c0.6,0,1,0.4,1,1s-0.4,1-1,1c-1.1,0-2,0.9-2,2C10,8.6,9.6,9,9,9z");
		hintSVG.setStrokeWidth(0.5);

		hintSVG.setScaleX(1.3875);
		hintSVG.setScaleY(1.3875);
		hintSVG.setTranslateX(3);
		hintSVG.setTranslateY(7);

		setSVG(hintSVG);
		refreshStyle();
	}

	/**
	 * Refreshes the style of the hint menu button based on the current style
	 * manager.
	 */
	@Override
	public void refreshStyle() {
		StyleManager styleManager = gameSessionContext.getStyleManager();

		if (isDisabled()) {
			svgPath.setStroke(styleManager.colorTextDisabled());
			svgPath.setFill(styleManager.colorTextDisabled());
		} else {
			svgPath.setStroke(styleManager.colorText());
			svgPath.setFill(styleManager.colorText());
		}
	}
}