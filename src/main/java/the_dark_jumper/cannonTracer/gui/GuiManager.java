package the_dark_jumper.cannonTracer.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.InputEvent;
import the_dark_jumper.cannonTracer.Main;
import the_dark_jumper.cannonTracer.modules.ModuleManager;

public class GuiManager {
	public final Main main;
	public final IngameGUI ingameGUI;
	public final ConfigGUI configGUI;
	public final HotkeyGUI hotkeyGUI;
	
	public GuiManager(Main main) {
		this.main = main;
		ingameGUI = new IngameGUI(this);
		configGUI = new ConfigGUI(this);
		hotkeyGUI = new HotkeyGUI(this);
	}
	
	public void renderGUIs() {
		if(main.moduleManager.state == ModuleManager.State.SINGLEPLAYER) {
			ingameGUI.renderScreen();
		}else if(main.moduleManager.state == ModuleManager.State.MULTIPLAYER) {
			ingameGUI.renderScreen();
		}
	}
	
	public void keyEvent(InputEvent.KeyInputEvent event) {
		Screen screen = Minecraft.getInstance().currentScreen;
		if(screen != null && screen instanceof JumperGUI) {
			((JumperGUI)screen).keyEvent(event);
		}
	}
	
	public void mousePressEvent(boolean isLeftDown) {
		Screen screen = Minecraft.getInstance().currentScreen;
		if(screen != null && screen instanceof JumperGUI) {
			((JumperGUI)screen).mousePressEvent(isLeftDown);
		}
	}
}
