package the_dark_jumper.cannonTracer.gui.guiElements;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import the_dark_jumper.cannonTracer.gui.JumperGUI;
import the_dark_jumper.cannonTracer.gui.JumperGUI.FrameConfig;
import the_dark_jumper.cannonTracer.gui.guiElements.interfaces.ClickableFrame;
import the_dark_jumper.cannonTracer.gui.guiElements.interfaces.RenderableFrame;

public class ScrollbarFrame implements RenderableFrame, ClickableFrame{
	public final JumperGUI parent;
	public final Minecraft minecraft;
	
	public FrameConfig config;
	@Override public FrameConfig getConfig() {return config;}
	
	public FrameColors colors;
	@Override public FrameColors getColors() {return colors;}
	
	public final Consumer<Double> onDragged;
	
	public boolean isClicked = false;
	@Override public boolean getIsClicked() {return isClicked;}
	@Override public void setIsClicked(boolean isClicked) {this.isClicked = isClicked;}
	
	public boolean hovered = false;
	@Override public boolean getHovered() {return hovered;}
	@Override public void setHovered(boolean hovered) {this.hovered = hovered;}
	
	public boolean isVertical = true;
	public double minScrollbarSize = 0.1;
	private double scrollbarSize = 1;
	public double scrollbarPos = 0;
	public void setScrollbarSize(double scrollbarSize) {
		if(scrollbarSize < minScrollbarSize) {
			this.scrollbarSize = minScrollbarSize;
		}else if(scrollbarSize <= 1) {
			this.scrollbarSize = scrollbarSize;
		}else {
			this.scrollbarSize = 1;
		}
	}
	public double getScrollbarSize() {
		return scrollbarSize;
	}
	
	public ScrollbarFrame(JumperGUI parent, FrameConfig config, FrameColors colors, @Nullable Consumer<Double> onDragged) {
		this.parent = parent;
		this.minecraft = parent.getMinecraft();
		this.config = config;
		this.colors = colors;
		this.onDragged = onDragged;
	}
	
	@Override
	public void mouseOver(int x, int y, int scaledScreenWidth, int scaledScreenHeight, boolean mouseLeftDown, boolean queueLeftUpdate) {
		ClickableFrame.super.mouseOver(x, y, scaledScreenWidth, scaledScreenHeight, mouseLeftDown, queueLeftUpdate);
		if(isClicked) {
			double current;
			double relative1;
			double relative2;
			if(isVertical) {
				//do vertical dragging
				current = y;
				relative1 = getPercentValue(scaledScreenHeight, config.y);
				relative2 = getPercentValue(scaledScreenHeight, config.yEnd);
			}else {
				//do horizontal dragging
				current = x;
				relative1 = getPercentValue(scaledScreenWidth, config.x);
				relative2 = getPercentValue(scaledScreenWidth, config.xEnd);
			}
			double scrollAreaStart = relative1 + ((relative2 - relative1) * scrollbarSize / 2);
			double scrollAreaEnd = relative2 - ((relative2 - relative1) * scrollbarSize / 2);
			if(current < scrollAreaStart) {
				doDrag(0);
			}else if(current > scrollAreaEnd) {
				doDrag(1);
			}else {
				doDrag((current - scrollAreaStart) / (scrollAreaEnd - scrollAreaStart));
			}
		}
	}
	
	public void doDrag(double pos) {
		if(pos == scrollbarPos) {
			return;
		}
		if(onDragged != null) {
			onDragged.accept(pos);
		}
		scrollbarPos = pos;
	}
	
	@Override
	public void doFills(int x1, int y1, int x2, int y2, int borderPx) {
		Screen.fill(x1, y1, x1 + borderPx, y2, colors.borderColor); //left edge
		Screen.fill(x1 + borderPx, y1, x2 - borderPx, y1 + borderPx, colors.borderColor); //top edge
		Screen.fill(x2 - borderPx, y1, x2, y2, colors.borderColor); //right edge
		Screen.fill(x1 + borderPx, y2 - borderPx, x2 - borderPx, y2, colors.borderColor); //bottom edge
		
		int relative1;
		int relative2;
		if(isVertical) {
			relative1 = y1;
			relative2 = y2;
		}else {
			relative1 = x1;
			relative2 = x2;
		}
		int halfBarSize = (int)((relative2 - relative1) * scrollbarSize / 2);
		int relativeBarPos = (int)(relative1 + halfBarSize + ((relative2 - relative1 - (2 * halfBarSize)) * scrollbarPos));
		if(isVertical) {
			Screen.fill(x1 + borderPx, relativeBarPos - halfBarSize + borderPx, x2 - borderPx, relativeBarPos + halfBarSize - borderPx, getInnerColor());
		}else {
			Screen.fill(relativeBarPos - halfBarSize + borderPx, y1 + borderPx, relativeBarPos + halfBarSize - borderPx, y2 - borderPx, getInnerColor());
		}
	}
}
