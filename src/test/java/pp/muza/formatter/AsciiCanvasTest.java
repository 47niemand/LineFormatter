package pp.muza.formatter;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AsciiCanvasTest {

    @Test
    void testClear() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        canvas.clear('.');
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals('.', canvas.get(i, j));
            }
        }
    }


    @Test
    void drawText() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        canvas.drawText(0, 0, "Hello");
        assertEquals('H', canvas.get(0, 0));
        assertEquals('e', canvas.get(1, 0));
        assertEquals('l', canvas.get(2, 0));
        assertEquals('l', canvas.get(3, 0));
        assertEquals('o', canvas.get(4, 0));
    }

    @Test
    void pasteLines() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        canvas.pasteLines(0, 0, List.of(new String[]{"Hello", "World"}));
        assertEquals('H', canvas.get(0, 0));
        assertEquals('e', canvas.get(1, 0));
        assertEquals('l', canvas.get(2, 0));
        assertEquals('l', canvas.get(3, 0));
        assertEquals('o', canvas.get(4, 0));
        assertEquals('W', canvas.get(0, 1));
        assertEquals('o', canvas.get(1, 1));
        assertEquals('r', canvas.get(2, 1));
        assertEquals('l', canvas.get(3, 1));
        assertEquals('d', canvas.get(4, 1));
    }

    @Test
    void getLines() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        canvas.pasteLines(0, 0, List.of(new String[]{"Hello", "World"}));
        List<String> lines = canvas.getLines();
        assertTrue(lines.get(0).startsWith("Hello"));
        assertTrue(lines.get(1).startsWith("World"));
    }

    @Test
    void getCanvas() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        canvas.pasteLines(0, 0, List.of(new String[]{"Hello", "World"}));
        char[][] c = canvas.getCanvas();
        assertEquals('H', c[0][0]);
        assertEquals('e', c[0][1]);
        assertEquals('l', c[0][2]);
        assertEquals('l', c[0][3]);
        assertEquals('o', c[0][4]);
        assertEquals('W', c[1][0]);
        assertEquals('o', c[1][1]);
        assertEquals('r', c[1][2]);
        assertEquals('l', c[1][3]);
        assertEquals('d', c[1][4]);
    }

    @Test
    void getWidth() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        assertEquals(10, canvas.getWidth());
    }

    @Test
    void getHeight() {
        AsciiCanvas canvas = new AsciiCanvas(10, 10);
        assertEquals(10, canvas.getHeight());
    }

    @Test
    void testToString() {
        AsciiCanvas canvas = new AsciiCanvas(5, 2);
        canvas.pasteLines(0, 0, List.of(new String[]{"Hello", "World"}));
        String s = canvas.toString();
        String expected = "Hello" + Meta.LINES_SEPARATOR + "World" + Meta.LINES_SEPARATOR;
        assertEquals(expected, s);
    }
}