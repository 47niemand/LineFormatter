package pp.muza.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Text-formatting utility class.
 */
public class LineFormatter {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Converts the list of strings to a single string with line separators.
     *
     * @param lines list of strings
     * @return string with line separators
     */
    public static String linesToString(List<String> lines) {
        return String.join(LINE_SEPARATOR, lines);
    }

    /**
     * Returns a string with the text wrapped to the specified line width.
     * if a word exceeds line width, it doesn't wrap.
     *
     * @param s     the text to wrap
     * @param width the width of the text
     * @param space the padding character
     * @return the wrapped text
     */
    public static List<String> textWrap(String s, int width, char space) {
        // split the string into words by delimiters
        String[] words = s.split("\\s+");
        // create a new string builder
        List<String> resut = new ArrayList<>();
        // create a new line
        StringBuilder line = new StringBuilder();
        // iterate over the words
        for (String word : words) {
            if (line.length() == 0) {
                // if the line is empty, add the word
                line = new StringBuilder(word);
            } else if (1 + word.length() + line.length() >= width) {
                // if the line is too long, add the line to the string builder and create a new line
                resut.add(line.toString());
                line = new StringBuilder(word);
            } else {
                // if the line is not too long, add the word to the line
                line.append(space).append(word);
            }
        }
        resut.add(line.toString());
        return resut;
    }

    /**
     * Returns a string with the text centered and trimmed to the specified width.
     *
     * @param s     the text to center and trim
     * @param width the width of the text
     * @return the centered and trimmed text
     */
    public static String centerTrim(String s, int width, char pad) {
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
     * Returns a string with the text padded to the specified width. The padding is added to the right.
     * If the text is longer than the specified width, the text is trimmed.
     *
     * @param s     the text to pad
     * @param width the width of the text
     * @return the padded text
     */
    public static String rightPadTrim(String s, int width, char pad) {
        String result;
        if (s.length() > width) {
            result = s.substring(0, width);
        } else {
            result = s + String.valueOf(pad).repeat(width - s.length());
        }
        return result;
    }

    /**
     * Returns a string with the text padded to the specified width. The padding is added to the left.
     * If the text is longer than the specified width, the text is trimmed.
     *
     * @param s     the text to pad
     * @param width the width of the text
     * @return the padded text
     */
    public static String leftPadTrim(String s, int width, char pad) {
        String result;
        if (s.length() > width) {
            result = s.substring(0, width);
        } else {
            result = String.valueOf(pad).repeat(width - s.length()) + s;
        }
        return result;
    }

    /**
     * Creates a rectangle with the specified dimensions, into which the text is fitted.
     *
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param text   the text to fit into the rectangle
     * @return the rectangle
     */
    public static List<String> textRectangle(int width, int height, String text, Border border, char pad) {
        int maxTextWidth = width - (border.left ? 1 : 0) - (border.right ? 1 : 0);
        List<String> lines = textWrap(text, width, pad);
        List<String> result = new ArrayList<>();
        int textHeight = lines.size();
        int textTop = (height - textHeight) / 2;
        StringBuilder sb = new StringBuilder();
        for (int j = 0, i = 0; i < height; i++) {
            if ((i == 0 && border.top)) {
                result.add(borderHorizontalLine(width, border, maxTextWidth));
            } else if (i == height - 1 && border.bottom) {
                result.add(borderHorizontalLine(width, border, maxTextWidth));
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
     * Creates a list of strings with the specified dimensions, into which the text is fitted.
     *
     * @param lines  the list of strings
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param align  the alignment of the text
     * @param pad    the padding character
     * @return the list of strings
     */
    public static List<String> resize(List<String> lines, int width, int height, Align align, char pad) {
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

    public static List<String> joinHorizontal(List<String> lines1, List<String> lines2) {
        int maxLines = Math.max(lines1.size(), lines2.size());
        List<String> result = new ArrayList<>();
        for (int i = 0; i < maxLines; i++) {
            result.add((i < lines1.size() ? lines1.get(i) : "") + (i < lines2.size() ? lines2.get(i) : ""));
        }
        return result;
    }

    public static List<String> joinVertical(List<String> lines1, List<String> lines2) {
        List<String> result = new ArrayList<>();
        result.addAll(lines1);
        result.addAll(lines2);
        return result;
    }

    private static String borderHorizontalLine(int width, Border border, int textWidth) {
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
                result.add(rightPadTrim(lines.get(j), width, pad));
            } else {
                result.add(String.valueOf(pad).repeat(width));
            }
        }
    }

    static void resizeRight(int width, int height, List<String> lines, List<String> result, int offset, char pad) {
        for (int i = 0; i < height; i++) {
            int j = i - offset;
            if (j >= 0 && j < lines.size()) {
                result.add(leftPadTrim(lines.get(j), width, pad));
            } else {
                result.add(String.valueOf(pad).repeat(width));
            }
        }
    }


    /**
     * Border location.
     */
    public enum Border {
        ALL(true, true, true, true),
        BOTTOM(false, false, false, true),
        BOTTOM_LEFT(true, false, false, true),
        BOTTOM_RIGHT(false, true, false, true),
        HORIZONTAL(false, false, true, true),
        LEFT(true, false, false, false),
        NONE(false, false, false, false),
        RIGHT(false, true, false, false),
        TOP(false, false, true, false),
        TOP_LEFT(true, false, true, false),
        TOP_RIGHT(false, true, true, false),
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
     * Crop align.
     */
    public enum Align {
        LEFT_BOTTOM, LEFT_TOP, RIGHT_BOTTOM, RIGHT_TOP
    }
}
