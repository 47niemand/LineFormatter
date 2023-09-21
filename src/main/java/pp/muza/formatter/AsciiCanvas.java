package pp.muza.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * AsciiCanvas is a class that can be used to draw ascii art.
 *
 * @author 47niemand
 */
public class AsciiCanvas {

    private final int width;
    private final int height;
    private final char[][] canvas;

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
        clear();
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
            }
        }
    }

    /**
     * Clears the canvas.
     **/
    public void clear() {
        this.clear(' ');
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(canvas[i][j]);
            }
            sb.append(Meta.LINES_SEPARATOR);
        }
        return sb.toString();
    }

    public enum Align {
        LEFT, RIGHT
    }
}
