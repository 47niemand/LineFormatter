package pp.muza.formatter;

import com.diogonunes.jcolor.AnsiFormat;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

/**
 * AsciiCanvas is a class that can be used to draw ascii art.
 *
 * @author 47niemand
 */
public class AsciiCanvas {

    /**
     * The default color of the canvas.
     */
    public static final AnsiFormat DEFAULT_COLOR = new AnsiFormat(BRIGHT_WHITE_TEXT(), GREEN_BACK(), NONE());
    /**
     * The character to fill the canvas with.
     */
    public static final char SPACE_CHAR = ' ';
    private final int width;
    private final int height;
    private final char[][] canvas;
    private final AnsiFormat[][] attributes;
    private boolean isColorMode = false;
    private AnsiFormat color = DEFAULT_COLOR;

    /**
     * Creates a new AsciiCanvas with the specified width and height.
     *
     * @param width  the width of the canvas.
     * @param height the height of the canvas.
     */
    public AsciiCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new char[height][width];
        attributes = new AnsiFormat[height][width];
        clear();
    }

    /**
     * Resets the current color to the default color.
     */
    public void resetColor() {
        this.color = DEFAULT_COLOR;
    }

    /**
     * @return if the canvas is colored.
     */
    public boolean isColorMode() {
        return isColorMode;
    }

    /**
     * Sets if the canvas is colored.
     *
     * @param isColorMode if the canvas is colored.
     */
    public void setColorMode(boolean isColorMode) {
        this.isColorMode = isColorMode;
    }

    /**
     * Sets the current color.
     *
     * @param color the color to set.
     */
    public void setColor(AnsiFormat color) {
        this.color = color == null ? DEFAULT_COLOR : color;
    }

    /**
     * Clears the canvas.
     *
     * @param c the character to fill the canvas with.
     **/
    public void clear(char c) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = c;
                attributes[i][j] = null;
            }
        }
    }

    /**
     * Clears the canvas.
     **/
    public void clear() {
        this.clear(SPACE_CHAR);
    }

    /**
     * Scrolls the canvas vertically.
     *
     * @param n the number of lines to scroll. Positive values scroll down, negative
     *          values scroll up.
     */
    public void scrollVertically(int n) {
        if (n == 0) {
            return;
        }
        if (n > height || n < -height) {
            clear();
            return;
        }
        if (n > 0) {
            for (int i = 0; i < height - n; i++) {
                System.arraycopy(canvas[i + n], 0, canvas[i], 0, width);
                System.arraycopy(attributes[i + n], 0, attributes[i], 0, width);
            }
            for (int i = height - n; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    canvas[i][j] = SPACE_CHAR;
                    attributes[i][j] = null;
                }
            }
        } else {
            for (int i = height - 1; i >= -n; i--) {
                System.arraycopy(canvas[i + n], 0, canvas[i], 0, width);
                System.arraycopy(attributes[i + n], 0, attributes[i], 0, width);
            }
            for (int i = 0; i < -n; i++) {
                for (int j = 0; j < width; j++) {
                    canvas[i][j] = SPACE_CHAR;
                    attributes[i][j] = null;
                }
            }
        }

    }

    /**
     * Draws a text at the specified position.
     *
     * @param left the left position of the text.
     * @param top  the top position of the text.
     * @param text the text to draw.
     */
    public void drawText(int left, int top, String text) {
        if (top < 0 || top >= height || left < 0 || left >= width || text == null || text.isEmpty()) {
            return;
        }
        int i = 0;
        for (char c : text.toCharArray()) {
            if (left + i >= width) {
                break;
            }
            canvas[top][left + i] = c;
            if (isColorMode) {
                attributes[top][left + i] = color;
            }
            i++;
        }
    }

    /**
     * Draws a text at the specified position with the specified alignment.
     *
     * @param left  the left position of the text.
     * @param top   the top position of the text.
     * @param text  the text to draw.
     * @param align the alignment of the text.
     */
    public void drawText(int left, int top, String text, Align align) {
        if (align == Align.LEFT) {
            drawText(left, top, text);
        } else if (align == Align.RIGHT) {
            drawText(left - text.length() + 1, top, text);
        } else {
            throw new IllegalArgumentException("Unknown alignment: " + align);
        }
    }

    /**
     * Draws lines of text at the specified position.
     *
     * @param left  the left position of the text.
     * @param top   the top position of the text.
     * @param lines the lines of text to draw.
     */
    public void pasteLines(int left, int top, List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            if (top + i >= height) {
                break;
            }
            drawText(left, top + i, lines.get(i));
        }
    }

    /**
     * returns the canvas as a list of strings.
     *
     * @return list of strings.
     */
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            lines.add(new String(canvas[i]));
        }
        return lines;
    }

    /**
     * returns the canvas as a character array.
     *
     * @return char array.
     */
    public char[][] getCanvas() {
        return canvas;
    }

    /**
     * returns the width of the canvas.
     *
     * @return width of the canvas.
     */
    public int getWidth() {
        return width;
    }

    /**
     * returns the height of the canvas.
     *
     * @return height of the canvas.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the character at the specified position.
     *
     * @param left the left position.
     * @param top  the top position.
     * @return the character at the specified position.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    public char get(int left, int top) {
        checkRange(left, width, "left");
        checkRange(top, height, "top");
        return canvas[top][left];
    }

    private void checkRange(int value, int max, String argument) {
        if (value < 0 || value >= max) {
            throw new IndexOutOfBoundsException(argument + " is out of bounds");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb;
        if (!isColorMode) {
            sb = new StringBuilder(width * (1 + height));
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    sb.append(canvas[i][j]);
                }
                sb.append(Meta.LINES_SEPARATOR);
            }
        } else {
            sb = new StringBuilder(width * (1 + height) * 6);
            AnsiFormat currentColor;
            StringBuilder line = new StringBuilder(width * 6);

            for (int i = 0; i < height; i++) {
                line.setLength(0);
                currentColor = attributes[i][0] == null ? DEFAULT_COLOR : attributes[i][0];
                for (int j = 0; j < width; j++) {
                    if (currentColor == (attributes[i][j] == null ? DEFAULT_COLOR : attributes[i][j])) {
                        line.append(canvas[i][j]);
                    } else {
                        sb.append(colorize(line.toString(), currentColor));
                        currentColor = attributes[i][j] == null ? DEFAULT_COLOR : attributes[i][j];
                        line.setLength(0);
                        line.append(canvas[i][j]);
                    }
                }
                sb.append(colorize(line.toString(), currentColor));
                sb.append(Meta.LINES_SEPARATOR);
            }
        }
        return sb.toString();
    }

    /**
     * Returns the color at the specified position.
     *
     * @param left the left position.
     * @param top  the top position.
     * @return the color at the specified position.
     */
    public AnsiFormat getColorAt(int left, int top) {
        checkRange(left, width, "left");
        checkRange(top, height, "top");
        return attributes[top][left];
    }

    /**
     * Aligns the text to the left or right.
     */
    public enum Align {
        /**
         * Aligns the text to the left.
         */
        LEFT,
        /**
         * Aligns the text to the right.
         */
        RIGHT
    }
}
