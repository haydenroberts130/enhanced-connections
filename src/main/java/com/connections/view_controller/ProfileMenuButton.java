package com.connections.view_controller;

import javafx.scene.shape.SVGPath;

/**
 * The ProfileMenuButton class represents a button with a profile icon in the
 * menu. It extends the SVGButton class.
 */
public class ProfileMenuButton extends SVGButton {

	/**
	 * Constructs a new ProfileMenuButton with the specified GameSessionContext.
	 *
	 * @param gameSessionContext the GameSessionContext used by the profile menu
	 *                           button
	 */
	public ProfileMenuButton(GameSessionContext gameSessionContext) {
		super(gameSessionContext);

		SVGPath profileIconSVG = new SVGPath();
		profileIconSVG.setContent(
				"M12.12 12.78C12.05 12.77 11.96 12.77 11.88 12.78C10.12 12.72 8.71997 11.28 8.71997 9.50998C8.71997 7.69998 10.18 6.22998 12 6.22998C13.81 6.22998 15.28 7.69998 15.28 9.50998C15.27 11.28 13.88 12.72 12.12 12.78Z M18.74 19.3801C16.96 21.0101 14.6 22.0001 12 22.0001C9.40001 22.0001 7.04001 21.0101 5.26001 19.3801C5.36001 18.4401 5.96001 17.5201 7.03001 16.8001C9.77001 14.9801 14.25 14.9801 16.97 16.8001C18.04 17.5201 18.64 18.4401 18.74 19.3801Z M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z");
		profileIconSVG.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
		profileIconSVG.setStrokeLineJoin(javafx.scene.shape.StrokeLineJoin.ROUND);
		profileIconSVG.setScaleX(1.66499174313);
		profileIconSVG.setScaleY(1.66499174313);

		profileIconSVG.setTranslateX(2);
		profileIconSVG.setTranslateY(7);
		profileIconSVG.setStrokeWidth(2);

		setSVG(profileIconSVG);
		refreshStyle();
	}

	/**
	 * Refreshes the style of the profile menu button based on the current style
	 * manager.
	 */
	@Override
	public void refreshStyle() {
		svgPath.setStroke(gameSessionContext.getStyleManager().colorText());
		svgPath.setFill(gameSessionContext.getStyleManager().colorWholeGameBackground());
	}
}