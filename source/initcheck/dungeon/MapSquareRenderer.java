package initcheck.dungeon;

import initcheck.InitImage;
import initcheck.utils.StrManip;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class MapSquareRenderer {

	Dungeon m;

	// set the default background color
	Color backgroundColor = Color.black;
	Color bg;
	Color fg;
	Color oldbg;
	Color bgv;

	Rectangle center = new Rectangle();
	Rectangle clipRect = new Rectangle();
	Rectangle rect = new Rectangle();
	GradientPaint gradient;
	TexturePaint texture;

	public MapSquareRenderer(Dungeon m) {
		this.m = m;
	}

	public void render(Graphics2D g, MapSquare currSquare, int x, int y,
			int level, int sz, int mode, int currentXPosition,
			int currentYPosition, int currentLevel, Square roomCorner1) {

		render(g, currSquare, x, y, level, sz, mode, 
				currentXPosition, currentYPosition, currentLevel, roomCorner1, true);
	}
	
	public void render(Graphics2D g, MapSquare currSquare, int x, int y,
			int level, int sz, int mode, int currentXPosition,
			int currentYPosition, int currentLevel, Square roomCorner1, boolean position) {

		// the actual x an y pixel positions
		int xpos = 0;
		int ypos = 0;
		if (position) {
			xpos = (x * sz);
			ypos = (y * sz);

			if (m.style == Dungeon.HEX) {
				if (y % 2 == 0) {
					xpos += (sz / 2);
				}
				ypos -= y * (sz / 3);
			}
		}
		
		int squareType = currSquare.getType();
		boolean visible = currSquare.isVisible(m.getCurrentGroup());

		
		rect.setBounds(xpos, ypos, sz, sz);
		clipRect.setBounds(0, 0, sz, sz);

		// if we are in client display mode, then we only want to paint
		// visited squares
		if (mode == DrawingBoard.CLIENT && !visible) {

			// paint unvisited squares black
			g.setColor(Color.black);
			if (m.style == Dungeon.SQUARE) {

				g.fill(rect);
			} else {
				g.fill(new Hexagon(sz, xpos, ypos));
			}

		} else {

			backgroundColor = Color.black;
			fg = currSquare.getForeground();
			bg = currSquare.getBackground();
			bgv = bg;

			if (visible) {
				bgv = bg.brighter().brighter();
			}

			oldbg = bg;

			// draw the default fill (this handles water)
			//if (currSquare.getRenderType() == 1) {
				if (visible && squareType != Dungeon.wall) {
					g.setColor(bg.brighter());
					if (m.style == Dungeon.SQUARE) {

						g.fill(rect);
					} else {
						g.fill(new Hexagon(sz, xpos, ypos));
					}
					g.setColor(fg.brighter());
					if (m.style == Dungeon.SQUARE) {

						g.draw(rect);
					} else {
						g.draw(new Hexagon(sz, xpos, ypos));
					}
				} else {

					g.setColor(bg);
					if (m.style == Dungeon.SQUARE) {

						g.fill(rect);
					} else {
						g.fill(new Hexagon(sz, xpos, ypos));
					}
					g.setColor(fg);
					if (m.style == Dungeon.SQUARE) {

						g.draw(rect);
					} else {
						g.draw(new Hexagon(sz, xpos, ypos));
					}

				}
			//}

			if (squareType == Dungeon.init) {
				return;
			}

			// draw stairs
			if (currSquare.isStair()) {

				switch (squareType) {
				case (Dungeon.stairnorth):
					drawstaircase(g, xpos, ypos, xpos + sz, ypos + sz,
							Dungeon.NORTH, Color.yellow);
					break;
				case (Dungeon.stairsouth):
					drawstaircase(g, xpos, ypos, xpos + sz, ypos + sz,
							Dungeon.SOUTH, Color.yellow);
					break;
				case (Dungeon.staireast):
					drawstaircase(g, xpos, ypos, xpos + sz, ypos + sz,
							Dungeon.EAST, Color.yellow);
					break;
				case (Dungeon.stairwest):
					drawstaircase(g, xpos, ypos, xpos + sz, ypos + sz,
							Dungeon.WEST, Color.yellow);
					break;
				case (Dungeon.staircircle):
					Polygon pol = new Polygon();
					pol.addPoint(xpos, ypos + (sz / 2));
					pol.addPoint(xpos + (sz / 2), ypos);
					pol.addPoint(xpos + sz, ypos + (sz / 2));

					Polygon down = new Polygon();
					down.addPoint(xpos, ypos + (sz / 2));
					down.addPoint(xpos + (sz / 2), ypos + sz);
					down.addPoint(xpos + sz, ypos + (sz / 2));

					fg = Color.yellow;
					if (squareType == Dungeon.air)
						fg = fg.darker().darker().darker();

					if (level > 0 && m.squares[x][y][level - 1].isStair()) {
						g.setColor(fg);
						// g.fill(new
						// Ellipse2D.Double(xpos+2,ypos+2,sz-4,sz-4));

					} else {
						g.setColor(Color.red);
					}
					g.fill(pol);
					if (level < m.asizez - 1
							&& m.squares[x][y][level + 1].isStair()) {

						g.setColor(fg);
					} else {

						g.setColor(Color.red);
					}
					g.fill(down);

					break;
				default:
					break;
				}
			}
			// draw bridges
			else if (squareType == Dungeon.bridge) {
				g.setColor(fg);
				rect.setBounds(xpos, ypos + 2, sz, sz - 3);
				g.fill(rect);
			}
			// draw holes
			else if (squareType == Dungeon.hole) {
				g.setColor(Color.black);
				g
						.fill(new Ellipse2D.Double(xpos + 2, ypos + 2, sz - 4,
								sz - 4));
			}

			// draw rooms
			else if (currSquare.isRoom()) {

				if (squareType == Dungeon.tlcorner) {
					gradient = new GradientPaint(xpos - 1, ypos - 1,
							backgroundColor, xpos + sz, ypos + sz, bgv, true); // true
																				// means
																				// to
																				// repeat
																				// pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.trcorner) {
					gradient = new GradientPaint(xpos, ypos + sz, bgv, xpos
							+ sz, ypos, backgroundColor, true); // true means to
																// repeat
																// pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.blcorner) {
					gradient = new GradientPaint(xpos, ypos + sz,
							backgroundColor, xpos + sz, ypos, bgv, true); // true
																			// means
																			// to
																			// repeat
																			// pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.brcorner) {
					gradient = new GradientPaint(xpos, ypos, bgv, xpos + sz,
							ypos + sz, backgroundColor, true); // true means to
																// repeat
																// pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.bottom) {
					gradient = new GradientPaint(xpos + (sz / 2), ypos, bgv,
							xpos + (sz / 2), ypos + sz, bg.darker().darker(),
							false); // true means to repeat pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.top) {
					gradient = new GradientPaint(xpos + (sz / 2), ypos, bg
							.darker().darker(), xpos + (sz / 2), ypos + sz,
							bgv, true); // true means to repeat pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.left) {
					gradient = new GradientPaint(xpos, ypos + (sz / 2), bg
							.darker().darker(), xpos + sz, ypos + (sz / 2),
							bgv, true); // true means to repeat pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				} else if (squareType == Dungeon.right) {
					gradient = new GradientPaint(xpos, ypos + (sz / 2), bgv,
							xpos + sz, ypos + (sz / 2), bg.darker().darker(),
							true); // true means to repeat pattern
					g.setPaint(gradient);
					g.fill(rect);
					g.setPaint(backgroundColor);
				}

				g.setColor(backgroundColor);
				g.draw(rect);
			}

			else if (squareType == Dungeon.land) {

				texture = new TexturePaint(InitImage.grass, clipRect); // true
																		// means
																		// to
																		// repeat
																		// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			else if (squareType == Dungeon.heavyforest) {

				texture = new TexturePaint(InitImage.trees, clipRect); // true
																		// means
																		// to
																		// repeat
																		// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			else if (currSquare.getRenderType() == MapSquare.TEXTURE
					&& currSquare.getPaletteType() != null) {
				texture = new TexturePaint(currSquare.getMapImage(), clipRect); // true
																				// means
																				// to
																				// repeat
																				// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			else if (squareType == Dungeon.lightforest) {
				BufferedImage img = InitImage.ltrees;
				// int rnd = RandomRoll.getRandom(3);
				// if (rnd == 1){
				// img = InitImage.ltrees2;
				// }else if (rnd == 2){
				// img = InitImage.ltrees3;
				// }
				texture = new TexturePaint(img, clipRect); // true means to
															// repeat pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			else if (squareType == Dungeon.town) {

				texture = new TexturePaint(InitImage.town, clipRect); // true
																		// means
																		// to
																		// repeat
																		// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			else if (squareType == Dungeon.medforest) {

				texture = new TexturePaint(InitImage.mtrees, clipRect); // true
																		// means
																		// to
																		// repeat
																		// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			// draw doors and corridors
			else if (squareType == Dungeon.door || currSquare.isCorridor()
					|| squareType == Dungeon.secretdoor) {

				
				texture = new TexturePaint(InitImage.corridor, clipRect); // true
																			// means
																			// to
																			// repeat
																			// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);

			}

			// draw doors. Don't draw secret doors on the client.
			if (currSquare.hasDoors()) {

				if (currSquare.hasNorthDoor()) {
					if (currSquare.getNorthDoor().isSecret()) {
						if (mode != DrawingBoard.CLIENT) {
							g.setColor(Color.red);
							rect.setBounds(xpos + sz / 5, ypos, sz * 3 / 5,
									sz / 5 + 1);
							g.fill(rect);
						}
					} else {
						g.setColor(Color.yellow);
						rect.setBounds(xpos + sz / 5, ypos, sz * 3 / 5,
								sz / 5 + 1);
						g.fill(rect);
					}

				}
				if (currSquare.hasSouthDoor()) {
					if (currSquare.getSouthDoor().isSecret()) {
						if (mode != DrawingBoard.CLIENT) {
							g.setColor(Color.red);
							rect.setBounds(xpos + sz / 5, sz * 4 / 5 + ypos,
									sz * 3 / 5, sz / 5 + 1);
							g.fill(rect);
						}
					} else {
						g.setColor(Color.yellow);
						rect.setBounds(xpos + sz / 5, sz * 4 / 5 + ypos,
								sz * 3 / 5, sz / 5 + 1);
						g.fill(rect);
					}
				}
				if (currSquare.hasEastDoor()) {
					if (currSquare.getEastDoor().isSecret()) {
						if (mode != DrawingBoard.CLIENT) {
							g.setColor(Color.red);
							rect.setBounds(xpos + sz * 4 / 5, ypos + sz / 5,
									sz / 5 + 1, sz * 3 / 5);
							g.fill(rect);
						}
					} else {
						g.setColor(Color.yellow);
						rect.setBounds(xpos + sz * 4 / 5, ypos + sz / 5,
								sz / 5 + 1, sz * 3 / 5);
						g.fill(rect);
					}
				}
				if (currSquare.hasWestDoor()) {
					if (currSquare.getWestDoor().isSecret()) {
						if (mode != DrawingBoard.CLIENT) {
							rect.setBounds(xpos, ypos + sz / 5, sz / 5 + 1,
									sz * 3 / 5);
							g.setColor(Color.red);
							g.fill(rect);
						}
					} else {
						g.setColor(Color.yellow);
						rect.setBounds(xpos, ypos + sz / 5, sz / 5 + 1,
								sz * 3 / 5);
						g.fill(rect);
					}
				}
			}

			// draw traps. Don't draw traps on the client.
			if (currSquare.hasTrap()
					&& (mode != DrawingBoard.CLIENT || currSquare.isVisited())) {

				texture = new TexturePaint(InitImage.trap, clipRect); // true
																		// means
																		// to
																		// repeat
																		// pattern
				rect.setBounds(xpos, ypos, sz, sz);
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
	
			}

			// draw vis overlay
			if (!visible) {

				texture = new TexturePaint(InitImage.visoverlay, clipRect); // true
																			// means
																			// to
																			// repeat
				rect.setBounds(xpos, ypos, sz, sz);														// pattern
				g.setPaint(texture);
				g.fill(rect);
				g.setPaint(backgroundColor);
				g.setColor(backgroundColor);
				g.draw(rect);
			}

			// long wrapStart = new java.util.Date().getTime();
			bg = oldbg;

			// mark squares that have notes
			if (currSquare.getNotes() != null
					&& !currSquare.getNotes().equals("")) {
				g.setColor(Color.cyan);
				g
						.fill(new Ellipse2D.Double(xpos + 2, ypos + 2, sz - 4,
								sz - 4));
			}
			
			if (!StrManip.isNullOrEmpty(currSquare.getMapLink())){
				g.setColor(Color.yellow);
				g.fill(rect);
			}

			// and the squares that have DM notes, if we are the DM
			if (mode != DrawingBoard.CLIENT && currSquare.getDmNotes() != null
					&& !currSquare.getDmNotes().equals("")) {

				g.setColor(Color.green);
				g
						.fill(new Ellipse2D.Double(xpos + 2, ypos + 2, sz - 4,
								sz - 4));
			}

			// fill in the party's current position in red
			if (x == currentXPosition && y == currentYPosition
					&& level == currentLevel) {

				g.setColor(Color.red);
				g
						.fill(new Ellipse2D.Double(xpos + 2, ypos + 2, sz - 4,
								sz - 4));
			}

			// mark the start of a room addtion in edit mode
			if (roomCorner1 != null && x == roomCorner1.getX()
					&& y == roomCorner1.getY() && level == roomCorner1.getZ()) {

				g.setColor(Color.blue);
				g.fill(rect);

			}

		}

	}

	private void drawstaircase(Graphics2D g, int left, int top, int right,
			int bottom, int dir, Color c) {

		// draws a directional staircase
		int steps = 10;
		int size = bottom - top;

		if (size / steps <= 2)
			steps = 5;
		if (size / steps <= 2)
			steps = 2;
		for (int i = 0; i < steps; i++) {
			c = c.darker();
			g.setColor(c);
			if (dir == Dungeon.NORTH)
				g.fill(new Rectangle(left, top + i * size / steps, size, size
						/ steps));
			if (dir == Dungeon.SOUTH)
				g.fill(new Rectangle(left,
						top + (steps - i - 1) * size / steps, size, size
								/ steps));
			if (dir == Dungeon.EAST)
				g.fill(new Rectangle(left + (steps - i - 1) * size / steps,
						top, size / steps, size));
			if (dir == Dungeon.WEST)
				g.fill(new Rectangle(left + i * size / steps, top,
						size / steps, size));
		}
		for (int i = 0; i < steps; i++) {
			g.setColor(Color.black);
			if (dir == Dungeon.NORTH)
				g.draw(new Rectangle(left, top + i * size / steps, size, size
						/ steps));
			if (dir == Dungeon.SOUTH)
				g.draw(new Rectangle(left,
						top + (steps - i - 1) * size / steps, size, size
								/ steps));
			if (dir == Dungeon.EAST)
				g.draw(new Rectangle(left + (steps - i - 1) * size / steps
						+ size / 5, top, size / steps, size));
			if (dir == Dungeon.WEST)
				g.draw(new Rectangle(left + i * size / steps, top,
						size / steps, size));
		}
	}

}
