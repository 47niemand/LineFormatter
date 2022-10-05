package pp.muza.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LineFormatterTest {

    @Test
    void textWrap() {
        String s = "tex1 tex2 tex3";
        List<String> expected = List.of("tex1\ntex2\ntex3".split("\n"));
        List<String> result = LineFormatter.textWrap(s, 4, ' ');
        Assertions.assertLinesMatch(expected, result);
    }

    @Test
    void centerTrim() {
        String s = "tex1";
        String expected = " tex1 ";
        Assertions.assertEquals(expected, LineFormatter.centerTrim(s, 6, ' '));

        s = "text";
        expected = "tex";
        Assertions.assertEquals(expected, LineFormatter.centerTrim(s, 3, ' '));
    }

    @Test
    void rightPadTrim() {
        String s = "text";
        String expected = "text  ";
        Assertions.assertEquals(expected, LineFormatter.rightPadTrim(s, 6, ' '));

        s = "text";
        expected = "tex";
        Assertions.assertEquals(expected, LineFormatter.rightPadTrim(s, 3, ' '));
    }

    @Test
    void printBox() {
        String s = "tex1 tex2";
        List<String> expected = List.of("+----+\n|tex1|\n+----+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 3, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("+----+\n|tex1|\n|tex2|\n+----+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("+----+\n|tex1|\n|tex2|\n|    |\n+----+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 5, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("+----+\n|    |\n|tex1|\n|tex2|\n|    |\n+----+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 6, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of();
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(0, 0, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(1, 1, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("++\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(2, 1, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("++\n++\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(2, 2, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("++\n++\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(2, 2, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("++\n||\n++\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(2, 3, s, LineFormatter.Border.ALL, ' ')));

        s = "1ex1 2ex2";
        expected = List.of("+-+\n|1|\n+-+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(3, 3, s, LineFormatter.Border.ALL, ' ')));

        s = "tex1 tex2";
        expected = List.of("------\n tex1 \n tex2 \n------\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.HORIZONTAL, ' ')));

        s = "tex1 tex2";
        expected = List.of("|    |\n|tex1|\n|tex2|\n|    |\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.VERTICAL, ' ')));

        s = "tex1 tex2";
        expected = List.of("|tex1|\n|tex2|\n|    |\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 3, s, LineFormatter.Border.VERTICAL, ' ')));

        s = "tex1 tex2";
        expected = List.of("------\n tex1 \n tex2 \n------\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.HORIZONTAL, ' ')));

        s = "tex1 tex2";
        expected = List.of("+-----\n|tex1 \n|tex2 \n|     \n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.TOP_LEFT, ' ')));

        s = "tex1 tex2";
        expected = List.of("-----+\ntex1 |\ntex2 |\n     |\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.TOP_RIGHT, ' ')));

        s = "tex1 tex2";
        expected = List.of("------\n tex1 \n tex2 \n      \n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.TOP, ' ')));

        s = "tex1 tex2";
        expected = List.of("     |\ntex1 |\ntex2 |\n-----+\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.BOTTOM_RIGHT, ' ')));

        s = "tex1 tex2";
        expected = List.of("|     \n|tex1 \n|tex2 \n+-----\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.BOTTOM_LEFT, ' ')));

        s = "tex1 tex2";
        expected = List.of("      \n tex1 \n tex2 \n------\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.BOTTOM, ' ')));

        s = "tex1 tex2";
        expected = List.of("|     \n|tex1 \n|tex2 \n|     \n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.LEFT, ' ')));
        
        s = "tex1 tex2";
        expected = List.of("     |\ntex1 |\ntex2 |\n     |\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.textRectangle(6, 4, s, LineFormatter.Border.RIGHT, ' ')));
    }

    @Test
    void fitLeft() {
        {
            List<String> s = List.of(new String[]{"text1", "text2"});
            List<String> expected = List.of("text1.\ntext2.\n......\n......\n".split("\n"));
            List<String> sb = new ArrayList<>();
            LineFormatter.resizeLeft(6, 4, s, sb, 0, '.');
            Assertions.assertLinesMatch(expected, (sb));
        }
        {
            List<String> s = List.of(new String[]{"text1", "text2"});
            List<String> expected = List.of("......\n......\ntext1.\ntext2.\n".split("\n"));
            List<String> sb = new ArrayList<>();
            LineFormatter.resizeLeft(6, 4, s, sb, 2, '.');
            Assertions.assertLinesMatch(expected, (sb));
        }
    }

    @Test
    void fitRight() {
        {
            List<String> s = List.of(new String[]{"text1", "text2"});
            List<String> expected =  List.of(".text1\n.text2\n......\n......\n".split("\n"));
            List<String> sb = new ArrayList<>();
            LineFormatter.resizeRight(6, 4, s, sb, 0, '.');
            Assertions.assertLinesMatch(expected, (sb));
        }
        {
            List<String> s = List.of(new String[]{"text1", "text2"});
            List<String> expected =  List.of("......\n......\n.text1\n.text2\n".split("\n"));
            List<String> sb = new ArrayList<>();
            LineFormatter.resizeRight(6, 4, s, sb, 2, '.');
            Assertions.assertLinesMatch(expected, (sb));
        }
    }

    @Test
    void fit() {
        List<String> s = List.of("text1\ntext2\n".split("\n"));

        List<String> expected = List.of(".text1\n.text2\n......\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.resize(s, 6, 3, LineFormatter.Align.RIGHT_TOP, '.')));

        expected = List.of("......\n.text1\n.text2\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.resize(s, 6, 3, LineFormatter.Align.RIGHT_BOTTOM, '.')));

        expected = List.of("text1.\ntext2.\n......\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.resize(s, 6, 3, LineFormatter.Align.LEFT_TOP, '.')));

        expected = List.of("......\ntext1.\ntext2.\n".split("\n"));
        Assertions.assertLinesMatch(expected, (LineFormatter.resize(s, 6, 3, LineFormatter.Align.LEFT_BOTTOM, '.')));
    }

}