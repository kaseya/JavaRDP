/* KeyCode.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:38 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: 
 */
package com.elusiva.rdp.keymapping; 

import org.apache.log4j.*;
import java.awt.event.*;

public class KeyCode
{
    /**
     * X scancodes for the printable keys of a standard 102 key MF-II Keyboard
     */
    public static final int SCANCODE_EXTENDED = 0x80;
    
    private final int[] main_key_scan_qwerty = {
	0x29, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D,
	0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B,
	0x1E, 0x1F, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x2B,
	0x2C, 0x2D, 0x2E, 0x2F, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35,
	0x56};
    
    private static final String[] main_key_US = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{", "]}",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "''\"", // added ' to \"
        "\\|",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?"
        };
        
        /*** United States keyboard layout (phantom key version) */
        /* (XFree86 reports the <> key even if it's not physically there) */
        private static final String[] main_key_US_phantom = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{", "]}",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "'\"",
        "\\|",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?",
        "<>"            /* the phantom key */
        };
        
        /*** British keyboard layout */
        private static final String[] main_key_UK = {
        "`¬|", "1!", "2\"", "3£", "4$¤", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{", "]}",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "'@", "#~",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?",
        "\\|"
        };
        
        /*** French keyboard layout (contributed by Eric Pouech) */
        private static final String[] main_key_FR = {
        "²", "&1", "é2~", "\"3#", "'4{", "(5[", "-6|", "è7", "_8\\", "ç9^±",
        "à0@", ")°]", "=+}",
        "aA", "zZ", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "^¨", "$£¤",
        "qQ", "sSß", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "mM", "ù%", "*µ",
        "wW", "xX", "cC", "vV", "bB", "nN", ",?", ";.", ":/", "!§",
        "<>"
        };

        /*** Icelandic keyboard layout (contributed by Ríkharður Egilsson) */
        private static final String[] main_key_IS = {
        "°", "1!", "2\"", "3#", "4$", "5%", "6&", "7/{", "8([", "9)]", "0=}",
        "öÖ\\", "-_",
        "qQ@", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "ðÐ",
        "'?~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "æÆ", "´^", "+*`",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "þÞ",
        "<>|"
        };
        
        /*** German keyboard layout (contributed by Ulrich Weigand) */
        private static final String[] main_key_DE = {
        "^°", "1!", "2\"²", "3§³", "4$", "5%", "6&", "7/{", "8([", "9)]", "0=}",
        "ß?\\", "'`",
        "qQ@", "wW", "eE€", "rR", "tT", "zZ", "uU", "iI", "oO", "pP", "üÜ",
        "+*~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "öÖ", "äÄ", "#´",
        "yY", "xX", "cC", "vV", "bB", "nN", "mMµ", ",;", ".:", "-_",
        "<>|"
        };

        /*** German keyboard layout without dead keys */
        private static final String[] main_key_DE_nodead = {
        "^°", "1!", "2\"", "3§", "4$", "5%", "6&", "7/{", "8([", "9)]", "0=}",
        "ß?\\", "´",
        "qQ", "wW", "eE", "rR", "tT", "zZ", "uU", "iI", "oO", "pP", "üÜ", "+*~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "öÖ", "äÄ", "#'",
        "yY", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>"
        };

        /*** Swiss German keyboard layout (contributed by Jonathan Naylor) */
        private static final String[] main_key_SG = {
        "§°", "1+|", "2\"@", "3*#", "4ç", "5%", "6&¬", "7/¦", "8(¢", "9)", "0=",
        "'?´", "^`~",
        "qQ", "wW", "eE", "rR", "tT", "zZ", "uU", "iI", "oO", "pP", "üè[",
        "¨!]",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "öé", "äà{",
        "$£}",
        "yY", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>\\"
        };

        /*** Swiss French keyboard layout (contributed by Philippe Froidevaux) */
        private static final String[] main_key_SF = {
        "§°", "1+|", "2\"@", "3*#", "4ç", "5%", "6&¬", "7/¦", "8(¢", "9)", "0=",
        "'?´", "^`~",
        "qQ", "wW", "eE", "rR", "tT", "zZ", "uU", "iI", "oO", "pP", "èü[",
        "¨!]",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "éö", "àä{",
        "$£}",
        "yY", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>\\"
        };

        /*** Norwegian keyboard layout (contributed by Ove Kåven) */
        private static final String[] main_key_NO = {
        "|§", "1!", "2\"@", "3#£", "4¤$", "5%", "6&", "7/{", "8([", "9)]",
        "0=}", "+?", "\\`´",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "åÅ", "¨^~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "øØ", "æÆ", "'*",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>"
        };
        
        /*** Danish keyboard layout (contributed by Bertho Stultiens) */
        private static final String[] main_key_DA = {
        "½§", "1!", "2\"@", "3#£", "4¤$", "5%", "6&", "7/{", "8([", "9)]",
        "0=}", "+?", "´`|",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "åÅ", "¨^~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "æÆ", "øØ", "'*",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>\\"
        };
        
        /*** Swedish keyboard layout (contributed by Peter Bortas) */
        private static final String[] main_key_SE = {
        "§½", "1!", "2\"@", "3#£", "4¤$", "5%", "6&", "7/{", "8([", "9)]",
        "0=}", "+?\\", "´`",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "åÅ", "¨^~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "öÖ", "äÄ", "'*",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>|"
        };

        /*** Canadian French keyboard layout */
        private static final String[] main_key_CF = {
        "#|\\", "1!±", "2\"@", "3/£", "4$¢", "5%¤", "6?¬", "7&¦", "8*²", "9(³",
        "0)¼", "-_½", "=+¾",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO§", "pP¶", "^^[",
        "¸¨]",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:~", "``{",
        "<>}",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",'-", ".", "éÉ",
        "«»°"
        };
        
        /*** Portuguese keyboard layout */
        private static final String[] main_key_PT = {
        "\\¦", "1!", "2\"@", "3#£", "4$§", "5%", "6&", "7/{", "8([", "9)]",
        "0=}", "'?", "«»",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "+*\\¨",
        "\\'\\`",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "çÇ", "ºª",
        "\\~\\^",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>"
        };

        /*** Italian keyboard layout */
        private static final String[] main_key_IT = {
        "\\|", "1!¹", "2\"²", "3£³", "4$¼", "5%½", "6&¾", "7/{", "8([", "9)]",
        "0=}", "'?`", "ì^~",
        "qQ@", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oOø", "pPþ", "èé[",
        "+*]",
        "aA", "sSß", "dDð", "fF", "gG", "hH", "jJ", "kK", "lL", "òç@", "à°#",
        "ù§",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mMµ", ",;", ".:·", "-_",
        "<>|"
        };

        /*** Finnish keyboard layout */
        private static final String[] main_key_FI = {
        "", "1!", "2\"@", "3#", "4$", "5%", "6&", "7/{", "8([", "9)]", "0=}",
        "+?\\", "\'`",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "", "\"^~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "", "", "'*",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>|"
        };
        
        /*** Russian keyboard layout (contributed by Pavel Roskin) */
        private static final String[] main_key_RU = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+",
        "qQÊê", "wWÃã", "eEÕõ", "rRËë", "tTÅå", "yYÎî", "uUÇç", "iIÛû", "oOÝý",
        "pPÚú", "[{Èè", "]}ßÿ",
        "aAÆæ", "sSÙù", "dD×÷", "fFÁá", "gGÐð", "hHÒò", "jJÏï", "kKÌì", "lLÄä",
        ";:Öö", "'\"Üü", "\\|",
        "zZÑñ", "xXÞþ", "cCÓó", "vVÍí", "bBÉé", "nNÔô", "mMØø", ",<Ââ", ".>Àà",
        "/?"
        };
        
        /*** Russian keyboard layout KOI8-R */
        private static final String[] main_key_RU_koi8r = {
        "()", "1!", "2\"", "3/", "4$", "5:", "6,", "7.", "8;", "9?", "0%", "-_",
        "=+",
        "Êê", "Ãã", "Õõ", "Ëë", "Åå", "Îî", "Çç", "Ûû", "Ýý", "Úú", "Èè", "ßÿ",
        "Ææ", "Ùù", "×÷", "Áá", "Ðð", "Òò", "Ïï", "Ìì", "Ää", "Öö", "Üü", "\\|",
        "Ññ", "Þþ", "Óó", "Íí", "Éé", "Ôô", "Øø", "Ââ", "Àà", "/?",
        "<>"            /* the phantom key */
        };
        
        /*** Spanish keyboard layout (contributed by José Marcos López) */
        private static final String[] main_key_ES = {
        "ºª\\", "1!|", "2\"@", "3·#", "4$", "5%", "6&¬", "7/", "8(", "9)", "0=",
        "'?", "¡¿",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "`^[",
        "+*]",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "ñÑ", "'¨{",
        "çÇ}",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>"
        };

        /*** Belgian keyboard layout ***/
        private static final String[] main_key_BE = {
        "", "&1|", "é2@", "\"3#", "'4", "(5", "§6^", "è7", "!8", "ç9{", "à0}",
        ")°", "-_",
        "aA", "zZ", "eE¤", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "^¨[",
        "$*]",
        "qQ", "sSß", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "mM", "ù%´",
        "µ£`",
        "wW", "xX", "cC", "vV", "bB", "nN", ",?", ";.", ":/", "=+~",
        "<>\\"
        };
        
        /*** Hungarian keyboard layout (contributed by Zoltán Kovács) */
        private static final String[] main_key_HU = {
        "0§", "1'~", "2\"·", "3+^", "4!¢", "5%°", "6/²", "7=`", "8(ÿ", "9)´",
        "öÖ½", "üÜ¨", "óÓ¸",
        "qQ\\", "wW|", "eE", "rR", "tT", "zZ", "uU", "iIÍ", "oOø", "pP", "õÕ÷",
        "úÚ×",
        "aA", "sSð", "dDÐ", "fF[", "gG]", "hH", "jJí", "kK³", "lL£", "éÉ$",
        "áÁß", "ûÛ¤",
        "yY>", "xX#", "cC&", "vV@", "bB{", "nN}", "mM", ",?;", ".:·", "-_*",
        "íÍ<"
        };

        /*** Polish (programmer's) keyboard layout ***/
        private static final String[] main_key_PL = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&§", "8*", "9(", "0)", "-_",
        "=+",
        "qQ", "wW", "eEêÊ", "rR", "tT", "yY", "uU", "iI", "oOóÓ", "pP", "[{",
        "]}",
        "aA±¡", "sS¶¦", "dD", "fF", "gG", "hH", "jJ", "kK", "lL³£", ";:", "'\"",
        "\\|",
        "zZ¿¯", "xX¼¬", "cCæÆ", "vV", "bB", "nNñÑ", "mM", ",<", ".>", "/?",
        "<>|"
        };
        
        /*** Croatian keyboard layout specific for me <jelly@srk.fer.hr> ***/
        private static final String[] main_key_HR_jelly = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{¹©",
        "]}ðÐ",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:èÈ", "'\"æÆ",
        "\\|¾®",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?",
        "<>|"
        };

        /*** Croatian keyboard layout ***/
        private static final String[] main_key_HR = {
        "¸¨", "1!", "2\"·", "3#^", "4$¢", "5%°", "6&²", "7/`", "8(ÿ", "9)´",
        "0=½", "'?¨", "+*¸",
        "qQ\\", "wW|", "eE", "rR", "tT", "zZ", "uU", "iI", "oO", "pP", "¹©÷",
        "ðÐ×",
        "aA", "sS", "dD", "fF[", "gG]", "hH", "jJ", "kK³", "lL£", "èÈ", "æÆß",
        "¾®¤",
        "yY", "xX", "cC", "vV@", "bB{", "nN}", "mM§", ",;", ".:", "-_/",
        "<>"
        };
        
        /*** Japanese 106 keyboard layout ***/
        private static final String[] main_key_JA_jp106 = {
        "1!", "2\"", "3#", "4$", "5%", "6&", "7'", "8(", "9)", "0~", "-=", "^~",
        "\\|",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "@`", "[{",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";+", ":*", "]}",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?",
        "\\_",
        };

        /*** Japanese pc98x1 keyboard layout ***/
        private static final String[] main_key_JA_pc98x1 = {
        "1!", "2\"", "3#", "4$", "5%", "6&", "7'", "8(", "9)", "0", "-=", "^`",
        "\\|",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "@~", "[{",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";+", ":*", "]}",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?",
        "\\_",
        };
        
        /*** Brazilian ABNT-2 keyboard layout (contributed by Raul Gomes Fernandes) */
        private static final String[] main_key_PT_br = {
        "'\"", "1!", "2@", "3#", "4$", "5%", "6\"", "7&", "8*", "9(", "0)",
        "-_", "=+",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "'`", "[{",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "çÇ", "~^", "]}",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?"
        };

        /*** US international keyboard layout (contributed by Gustavo Noronha (kov@debian.org)) */
        private static final String[] main_key_US_intl = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+", "\\|",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{", "]}",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "'\"",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?"
        };

        /*** Slovak keyboard layout (see cssk_ibm(sk_qwerty) in xkbsel)
         - dead_abovering replaced with degree - no symbol in iso8859-2
         - brokenbar replaced with bar                  */
        private static final String[] main_key_SK = {
        ";°`'", "+1", "µ2", "¹3", "è4", "»5", "¾6", "ý7", "á8", "í9", "é0)",
        "=%", "",
        "qQ\\", "wW|", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "ú/÷",
        "ä(×",
        "aA", "sSð", "dDÐ", "fF[", "gG]", "hH", "jJ", "kK³", "lL£", "ô\"$",
        "§!ß", "ò)¤",
        "zZ>", "xX#", "cC&", "vV@", "bB{", "nN}", "mM", ",?<", ".:>", "-_*",
        "<>\\|"
        };

        /*** Slovak and Czech (programmer's) keyboard layout (see cssk_dual(cs_sk_ucw)) */
        private static final String[] main_key_SK_prog = {
        "`~", "1!", "2@", "3#", "4$", "5%", "6^", "7&", "8*", "9(", "0)", "-_",
        "=+",
        "qQäÄ", "wWìÌ", "eEéÉ", "rRøØ", "tT»«", "yYýÝ", "uUùÙ", "iIíÍ", "oOóÓ",
        "pPöÖ", "[{", "]}",
        "aAáÁ", "sS¹©", "dDïÏ", "fFëË", "gGàÀ", "hHúÚ", "jJüÜ", "kKôÔ", "lLµ¥",
        ";:", "'\"", "\\|",
        "zZ¾®", "xX¤", "cCèÈ", "vVçÇ", "bB", "nNòÒ", "mMåÅ", ",<", ".>", "/?",
        "<>"
        };
        
        /*** Czech keyboard layout (see cssk_ibm(cs_qwerty) in xkbsel) */
        private static final String[] main_key_CS = {
        ";", "+1", "ì2", "¹3", "è4", "ø5", "¾6", "ý7", "á8", "í9", "é0½)", "=%",
        "",
        "qQ\\", "wW|", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "ú/[{",
        ")(]}",
        "aA", "sSð", "dDÐ", "fF[", "gG]", "hH", "jJ", "kK³", "lL£", "ù\"$",
        "§!ß", "¨'",
        "zZ>", "xX#", "cC&", "vV@", "bB{", "nN}", "mM", ",?<", ".:>", "-_*",
        "<>\\|"
        };

        /*** Latin American keyboard layout (contributed by Gabriel Orlando Garcia) */
        private static final String[] main_key_LA = {
        "|°¬", "1!", "2\"", "3#", "4$", "5%", "6&", "7/", "8(", "9)", "0=",
        "'?\\", "¡¿",
        "qQ@", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "´¨",
        "+*~",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "ñÑ", "{[^",
        "}]`",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",;", ".:", "-_",
        "<>"
        };

        /*** Lithuanian (Baltic) keyboard layout (contributed by Nerijus Baliûnas) */
        private static final String[] main_key_LT_B = {
        "`~", "àÀ", "èÈ", "æÆ", "ëË", "áÁ", "ðÐ", "øØ", "ûÛ", "((", "))", "-_",
        "þÞ",
        "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{", "]}",
        "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "'\"",
        "\\|",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",<", ".>", "/?"
        };

        /*** Turkish keyboard Layout */
        private static final String[] main_key_TK = {
        "\"é", "1!", "2'", "3^#", "4+$", "5%", "6&", "7/{", "8([", "9)]", "0=}",
        "*?\\", "-_",
        "qQ@", "wW", "eE", "rR", "tT", "yY", "uU", "ýIî", "oO", "pP", "ðÐ",
        "üÜ~",
        "aAæ", "sSß", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", "þÞ", "iÝ",
        ",;`",
        "zZ", "xX", "cC", "vV", "bB", "nN", "mM", "öÖ", "çÇ", ".:"
        };
}
