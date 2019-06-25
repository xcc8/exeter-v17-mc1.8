package wtf.nuf.exeter.mcapi.utilities.minecraft.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * this was not made by me, modified version of NahrFont
 *
 * @author Nahr
 */
public class CustomFont extends Gui {
    private final Graphics2D graphics2D;
    private FontMetrics fontMetrics;
    private float extraSpacing;
    private int startChar;
    private float[] xPos, yPos;
    private ResourceLocation resourceLocation;
    private final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OG]"),
            patternUnsupported = Pattern.compile("(?i)\\u00A7[K-O]");

    private CustomFont(Object font, float size, float spacing) {
        this.startChar = 32;
        int endChar = 255;
        this.extraSpacing = spacing;
        this.xPos = new float[endChar - this.startChar];
        this.yPos = new float[endChar - this.startChar];

        BufferedImage bufferedImage = new BufferedImage(256, 256, 2);

        this.graphics2D = ((Graphics2D) bufferedImage.getGraphics());
        this.graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font theFont;
        try {
            if (font instanceof Font) {
                theFont = ((Font) font);
            } else if (font instanceof File) {
                theFont = Font.createFont(Font.PLAIN, (File) font).deriveFont(size);
            } else if (font instanceof InputStream) {
                theFont = Font.createFont(Font.PLAIN, (InputStream) font).deriveFont(size);
            } else if (font instanceof String) {
                theFont = new Font((String) font, Font.PLAIN, Math.round(size));
            } else {
                theFont = new Font("Verdana", Font.PLAIN, Math.round(size));
            }
            this.graphics2D.setFont(theFont);
        } catch (Exception exception) {
            exception.printStackTrace();
            theFont = new Font("Verdana", Font.PLAIN, Math.round(size));
            this.graphics2D.setFont(theFont);
        }
        this.graphics2D.setColor(new Color(255, 255, 255, 0));
        this.graphics2D.fillRect(0, 0, 256, 256);
        this.graphics2D.setColor(Color.white);
        this.fontMetrics = this.graphics2D.getFontMetrics();

        float x = 5F;
        float y = 5F;

        for (int i = this.startChar; i < endChar; i++) {
            this.graphics2D.drawString(Character.toString((char) i), x, y + this.fontMetrics.getAscent());
            this.xPos[(i - this.startChar)] = x;
            this.yPos[(i - this.startChar)] = (y - this.fontMetrics.getMaxDescent());
            x += this.fontMetrics.stringWidth(Character.toString((char) i)) + 2F;
            if (x >= 250 - this.fontMetrics.getMaxAdvance()) {
                x = 5F;
                y += this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent() + size / 2F;
            }
        }
        this.resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(
                "font" + font.toString() + size, new DynamicTexture(bufferedImage));
    }

    private CustomFont(Object font, float size) {
        this(font, size, 0F);
    }

    public CustomFont(Object font) {
        this(font, 20F, 0F);
    }

    public void drawString(String text, float x, float y, FontType fontType, int color, int color2) {
        GL11.glPushMatrix();
        text = stripUnsupported(text);

        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        String text2 = stripControlCodes(text);
        switch (fontType.ordinal()) {
            case 1:
                drawer(text2, x + 0.5F, y, color2);
                drawer(text2, x - 0.5F, y, color2);
                drawer(text2, x, y + 0.5F, color2);
                drawer(text2, x, y - 0.5F, color2);
                break;
            case 2:
                drawer(text2, x + 0.5F, y + 0.5F, color2);
                break;
            case 3:
                drawer(text2, x + 0.5F, y + 1F, color2);
                break;
            case 4:
                drawer(text2, x, y + 0.5F, color2);
                break;
            case 5:
                drawer(text2, x, y - 0.5F, color2);
                break;
        }
        drawer(text, x, y, color);
        GL11.glScalef(2F, 2F, 2F);
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public void drawString(String text, float x, float y, FontType fontType, int color) {
        drawString(text, x, y, fontType, color, 0xAA000000);
    }

    public void drawString(String text, float x, float y, int color) {
        drawString(text, x, y, FontType.SHADOW_THIN, color);
    }

    public void drawCenteredString(String text, float x, float y, FontType fontType, int color) {
        drawString(text, (x - getStringWidth(text) / 2), y, fontType, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        drawString(text, (x - getStringWidth(text) / 2), y, FontType.SHADOW_THIN, color);
    }

    private void drawer(String text, float x, float y, int color) {
        x *= 2F;
        y *= 2F;
        GL11.glEnable(3553);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);

        float alpha = (color >> 24 & 0xFF) / 255F;
        float red = (color >> 16 & 0xFF) / 255F;
        float green = (color >> 8 & 0xFF) / 255F;
        float blue = (color & 0xFF) / 255F;

        GL11.glColor4f(red, green, blue, alpha);
        float startX = x;
        for (int index = 0; index < text.length(); index++) {
            if ((text.charAt(index) == '�') && (index + 1 < text.length())) {
                char oneMore = Character.toLowerCase(text.charAt(index + 1));
                if (oneMore == 'n') {
                    y += this.fontMetrics.getAscent() + 2;
                    x = startX;
                }
                int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                if (colorCode < 16) {
                    try {
                        int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
                        GL11.glColor4f((newColor >> 16) / 255F, (newColor >> 8 & 0xFF) / 255F,
                                (newColor & 0xFF) / 255F, alpha);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (oneMore == 'f') {
                    GL11.glColor4f(1F, 1F, 1F, alpha);
                } else if (oneMore == 'r') {
                    GL11.glColor4f(red, green, blue, alpha);
                } else if (oneMore == 'g') {
                    GL11.glColor4f(0.47F, 0.67F, 0.27F, alpha);
                }
                index++;
            } else {
                try {
                    char character = text.charAt(index);
                    drawCharacter(character, x, y);
                    x += getStringWidth(Character.toString(character)) * 2F;
                } catch (ArrayIndexOutOfBoundsException indexException) {
                    char character = text.charAt(index);
                    System.out.println(String.format("Can't draw character: (%s)", Character.getNumericValue(character)));
                }
            }
        }
    }

    public float getStringWidth(String text) {
        return (float) (getBounds(text).getWidth() + this.extraSpacing) / 2F;
    }

    public float getStringHeight(String text) {
        return (float) getBounds(text).getHeight() / 2F;
    }

    private Rectangle2D getBounds(String text) {
        return this.fontMetrics.getStringBounds(text, this.graphics2D);
    }

    private void drawCharacter(char character, float x, float y) throws ArrayIndexOutOfBoundsException {
        Rectangle2D bounds = this.fontMetrics.getStringBounds(Character.toString(character), this.graphics2D);
        drawTexturedModalRect((int) x, (int) y, (int) this.xPos[(character - this.startChar)],
                (int) this.yPos[(character - this.startChar)], (int) bounds.getWidth(),
                (int) bounds.getHeight() + this.fontMetrics.getMaxDescent() + 1);
    }

    private String wrapFormattedStringToWidth(String text, float width) {
        int wrapWidth = sizeStringToWidth(text, width);
        if (text.length() <= wrapWidth) {
            return text;
        }
        String split = text.substring(0, wrapWidth);
        String split2 = getFormatFromString(split) + text.substring(wrapWidth + ((text.charAt(wrapWidth) == ' ') ||
                (text.charAt(wrapWidth) == '\n') ? 1 : 0));
        try {
            return String.format("%s\n%s", split, wrapFormattedStringToWidth(split2, width));
        } catch (Exception e) {
            System.out.println("Cannot wrap string to width.");
        }
        return "";
    }

    private int sizeStringToWidth(String par1Str, float par2) {
        int length = par1Str.length();
        float var4 = 0F;
        int index = 0;
        int var6 = -1;
        for (boolean var7 = false; index < length; index++) {
            char var8 = par1Str.charAt(index);
            switch (var8) {
                case '\n':
                    index--;
                    break;
                case '�':
                    if (index < length - 1) {
                        index++;
                        char var9 = par1Str.charAt(index);
                        if ((var9 != 'l') && (var9 != 'L')) {
                            if ((var9 == 'r') || (var9 == 'R') || (isFormatColor(var9))) {
                                var7 = false;
                            }
                        } else {
                            var7 = true;
                        }
                    }
                    break;
                case ' ':
                    var6 = index;
                case '-':
                    var6 = index;
                case '_':
                    var6 = index;
                case ':':
                    var6 = index;
                default:
                    String text = String.valueOf(var8);
                    var4 += getStringWidth(text);
                    if (var7) {
                        var4 += 1F;
                    }
                    break;
            }
            if (var8 == '\n') {
                index++;
                var6 = index;
            } else {
                if (var4 > par2) {
                    break;
                }
            }
        }
        return (index != length) && (var6 != -1) && (var6 < index) ? var6 : index;
    }

    private String getFormatFromString(String text) {
        String formatFromString = "";
        int index = -1;
        int length = text.length();
        while ((index = text.indexOf('�', index + 1)) != -1) {
            if (index < length - 1) {
                char character = text.charAt(index + 1);
                if (isFormatColor(character)) {
                    formatFromString = "�" + character;
                } else if (isFormatSpecial(character)) {
                    formatFromString = formatFromString + "�" + character;
                }
            }
        }
        return formatFromString;
    }

    private boolean isFormatColor(char character) {
        return ((character >= '0') && (character <= '9')) || ((character >= 'a') && (character <= 'f')) ||
                ((character >= 'A') && (character <= 'F'));
    }

    private boolean isFormatSpecial(char character) {
        return ((character >= 'k') && (character <= 'o')) || ((character >= 'K') && (character <= 'O')) ||
                (character == 'r') || (character == 'R');
    }

    private String stripControlCodes(String text) {
        return this.patternControlCode.matcher(text).replaceAll("");
    }

    private String stripUnsupported(String text) {
        return this.patternUnsupported.matcher(text).replaceAll("");
    }

    public enum FontType {
        NORMAL, SHADOW_THICK, SHADOW_THIN, OUTLINE_THIN, EMBOSS_TOP, EMBOSS_BOTTOM
    }
}