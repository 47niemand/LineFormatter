package pp.muza.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Text-formatting utility class.
 *
 * @author 47niemand
 */
public final class LineFormatter {

    private LineFormatter() {
    }

    /**
     * Converts the list of strings to a single string with line separators.
     *
     * @param lines list of strings
     * @return string with line separators
     */
    public static String linesToString(List<String> lines) {
        return String.join(Meta.LINES_SEPARATOR, lines);
    }

    /**
     * Returns a lines with the text wrapped to the specified line width.
     * if a single word exceeds line width, it doesn't wrap.
     * if a text contains line separators, it starts a new line.
     *
     * @param text  the text to wrap
     * @param width the width of the text
     * @param space the padding character
     * @return the wrapped text
     * @throws IllegalArgumentException if the width is less than or equal to 0.
     */
    public static List<String> textWrap(String text, int width, char space) {
        // split the string into words by delimiters
        String[] sourceLines = text.split(Meta.LINE_DELIMITER_REGEX);
        List<String> result = new ArrayList<>();

        for (String s1 : sourceLines) {
            String t = s1.trim();
            String[] words = t.split(Meta.WORDS_DELIMITER);
            // create a new string builder

            // create a new line
            StringBuilder line = new StringBuilder();
            // iterate over the words
            for (String word : words) {
                if (line.length() == 0) {
                    // if the line is empty, add the word
                    line = new StringBuilder(word);
                } else if (1 + word.length() + line.length() >= width) {
                    // if the line is too long, add the line to the string builder and create a new
                    // line
                    result.add(line.toString());
                    line = new StringBuilder(word);
                } else {
                    // if the line is not too long, add the word to the line
                    line.append(space).append(word);
                }
            }
            result.add(line.toString());
        }
        return result;
    }

    /**
     * Returns a string with the text centered and trimmed to the specified width.
     *
     * @param s     the text to center and trim
     * @param width the width of the text
     * @param pad   the padding character
     * @return the centered and trimmed text
     * @throws IllegalArgumentException if the width is less than 1
     */
    public static String centerTrim(String s, int width, char pad) {
        checkPositive(width, "width");
        String result;
        if (s.length() >= width) {
            result = s.substring(0, width);
        } else {
            int left = (width - s.length()) / 2;
            int right = width - s.length() - left;
            String padStr = String.valueOf(pad);
            result = padStr.repeat(left) + s + padStr.repeat(right);
        }
        return result;
    }

    /**
     * Returns a string with the text padded to the specified width.
     * The padding is added to the right.
     * If the text is longer than the specified width, the text is trimmed.
     *
     * @param s     the text to pad
     * @param width the width of the text
     * @param pad   the padding character
     * @return the padded text
     * @throws IllegalArgumentException if the width is less than 1
     */
    public static String rightAlignTrim(String s, int width, char pad) {
        checkPositive(width, "width");
        String result;
        if (s.length() > width) {
            result = s.substring(0, width);
        } else {
            result = s + String.valueOf(pad).repeat(width - s.length());
        }
        return result;
    }

    /**
     * Returns a string with the text padded to the specified width.
     * The padding is added to the left.
     * If the text is longer than the specified width, the text is trimmed from the
     * left.
     *
     * @param s     the text to pad
     * @param width the width of the text
     * @param pad   the padding character
     * @return the padded text
     * @throws IllegalArgumentException if the width is less than 1
     */
    public static String leftAlignTrim(String s, int width, char pad) {
        checkPositive(width, "width");
        String result;
        if (s.length() > width) {
            result = s.substring(s.length() - width);
        } else {
            result = String.valueOf(pad).repeat(width - s.length()) + s;
        }
        return result;
    }

    /**
     * Creates a rectangle with the specified dimensions, into which the text is
     * fitted.
     *
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param text   the text to fit into the rectangle
     * @param border the border
     * @param pad    the padding character
     * @return the rectangle
     * @throws IllegalArgumentException if the width or height is less than 1
     */
    public static List<String> textRectangle(int width, int height, String text, Border border, char pad) {
        checkPositive(width, "width");
        checkPositive(height, "height");
        int maxTextWidth = width - (border.left ? 1 : 0) - (border.right ? 1 : 0);
        List<String> lines = textWrap(text, maxTextWidth, pad);
        List<String> result = new ArrayList<>();
        int textHeight = lines.size();
        int textTop = (height - textHeight) / 2;
        StringBuilder sb = new StringBuilder();
        for (int j = 0, i = 0; i < height; i++) {
            if ((i == 0 && border.top)) {
                result.add(horizontalLine(width, border));
            } else if (i == height - 1 && border.bottom) {
                result.add(horizontalLine(width, border));
            } else {
                if (border.left && width > 0) {
                    sb.append("|");
                }
                if (maxTextWidth > 0) {
                    if (i >= textTop && j < lines.size()) {
                        sb.append(centerTrim(lines.get(j), maxTextWidth, pad));
                        j++;
                    } else {
                        sb.append(String.valueOf(pad).repeat(maxTextWidth));
                    }
                }
                if (border.right && width > 1) {
                    sb.append("|");
                }
                result.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        return result;
    }

    /**
     * Creates a list of strings with the specified dimensions, into which the text
     * is fitted.
     *
     * @param lines  the list of strings
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param align  the alignment of the text
     * @param pad    the padding character
     * @return the list of strings
     * @throws IllegalArgumentException if the width or height is less than 1
     */
    public static List<String> resize(List<String> lines, int width, int height, Align align, char pad) {
        checkPositive(width, "width");
        checkPositive(height, "height");
        List<String> result = new ArrayList<>();
        switch (align) {
            case LEFT_BOTTOM:
                resizeLeft(width, height, lines, result, height - lines.size(), pad);
                break;
            case LEFT_TOP:
                resizeLeft(width, height, lines, result, 0, pad);
                break;
            case RIGHT_BOTTOM:
                resizeRight(width, height, lines, result, height - lines.size(), pad);
                break;
            case RIGHT_TOP:
                resizeRight(width, height, lines, result, 0, pad);
                break;
            default:
                throw new UnsupportedOperationException("Align " + align + " not supported");
        }
        return result;
    }

    /**
     * Joins lines into a single line.
     *
     * @param left  left lines
     * @param right right lines
     * @return the joined lines
     */
    public static List<String> joinHorizontal(List<String> left, List<String> right) {
        int maxLines = Math.max(left.size(), right.size());
        List<String> result = new ArrayList<>();
        // join the lines
        for (int i = 0; i < maxLines; i++) {
            result.add((i < left.size() ? left.get(i) : "") + (i < right.size() ? right.get(i) : ""));
        }
        return result;
    }

    /**
     * Joins two blocks of lines vertically.
     *
     * @param top    the top lines
     * @param bottom the bottom lines
     * @return the joined lines
     */
    public static List<String> joinVertical(List<String> top, List<String> bottom) {
        List<String> result = new ArrayList<>();
        result.addAll(top);
        result.addAll(bottom);
        return result;
    }

    /**
     * Creates a horizontal line
     *
     * @param width  the length of the line
     * @param border the border
     * @return the line
     * @throws IllegalArgumentException if the width is less than 1
     */
    public static String horizontalLine(int width, Border border) {
        checkPositive(width, "width");
        int textWidth = width - (border.left ? 1 : 0) - (border.right ? 1 : 0);
        StringBuilder sb = new StringBuilder();
        if (border.left && width > 0) {
            sb.append("+");
        }
        if (textWidth > 0) {
            sb.append("-".repeat(textWidth));
        }
        if (border.right && width > 1) {
            sb.append("+");
        }
        return sb.toString();
    }

    static void resizeLeft(int width, int height, List<String> lines, List<String> result, int offset, char pad) {
        for (int i = 0; i < height; i++) {
            int j = i - offset;
            if (j >= 0 && j < lines.size()) {
                result.add(rightAlignTrim(lines.get(j), width, pad));
            } else {
                result.add(String.valueOf(pad).repeat(width));
            }
        }
    }

    static void resizeRight(int width, int height, List<String> lines, List<String> result, int offset, char pad) {
        for (int i = 0; i < height; i++) {
            int j = i - offset;
            if (j >= 0 && j < lines.size()) {
                result.add(leftAlignTrim(lines.get(j), width, pad));
            } else {
                result.add(String.valueOf(pad).repeat(width));
            }
        }
    }

    private static void checkPositive(int value, String argument) {
        if (value < 0) {
            throw new IllegalArgumentException(argument + " must be positive");
        }
    }

    /**
     * Border location.
     */
    public enum Border {
        /**
         * All border.
         */
        ALL(true, true, true, true),
        /**
         * Bottom border.
         */
        BOTTOM(false, false, false, true),
        /**
         * Bottom left border.
         */
        BOTTOM_LEFT(true, false, false, true),
        /**
         * Bottom right border.
         */
        BOTTOM_RIGHT(false, true, false, true),
        /**
         * Horizontal border.
         */
        HORIZONTAL(false, false, true, true),
        /**
         * left border.
         */
        LEFT(true, false, false, false),
        /**
         * No border.
         */
        NONE(false, false, false, false),
        /**
         * Right border.
         */
        RIGHT(false, true, false, false),
        /**
         * Top border.
         */
        TOP(false, false, true, false),
        /**
         * Top-left border.
         */
        TOP_LEFT(true, false, true, false),
        /**
         * Top right border.
         */
        TOP_RIGHT(false, true, true, false),
        /**
         * Vertical border.
         */
        VERTICAL(true, true, false, false);

        public final boolean left;
        public final boolean right;
        public final boolean top;
        public final boolean bottom;

        Border(boolean left, boolean right, boolean top, boolean bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }

    /**
     * Resize align.
     */
    public enum Align {
        /**
         * Align to a left bottom corner.
         */
        LEFT_BOTTOM,
        /**
         * Align to a left top corner.
         */
        LEFT_TOP,
        /**
         * Align to a right bottom corner.
         */
        RIGHT_BOTTOM,
        /**
         * Align to a right top corner.
         */
        RIGHT_TOP
    }
}
