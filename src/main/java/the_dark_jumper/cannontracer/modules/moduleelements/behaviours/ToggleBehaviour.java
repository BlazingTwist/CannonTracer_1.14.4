package the_dark_jumper.cannontracer.modules.moduleelements.behaviours;

import jumpercommons.GetterAndSetter;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import the_dark_jumper.cannontracer.modules.moduleelements.IModule;
import the_dark_jumper.cannontracer.modules.moduleelements.ModuleBasic;

public class ToggleBehaviour implements IModuleBehaviour {
	private ModuleBasic parent;

	@Override
	public IModule getParent() {
		return parent;
	}

	public GetterAndSetter<Boolean> valueGNS;

	public ToggleBehaviour(ModuleBasic parent, boolean state, GetterAndSetter<Boolean> valueGNS) {
		this.parent = parent;
		if(valueGNS.get() != state){ // only alert if state actually changes
			valueGNS.set(state);
		}
		this.valueGNS = valueGNS;
	}

	@Override
	public void renderText(Screen screen, FontRenderer fontRenderer, int x, int y) {
		screen.drawString(fontRenderer, parent.getName() + " : " + valueGNS.get(), x, y, valueGNS.get() ? 0x7f00ff00 : 0x7fff0000);
	}

	@Override
	public void onTriggerChanged(boolean isTriggered) {
		if (isTriggered) {
			toggle();
		}
	}

	public void toggle() {
		valueGNS.set(!valueGNS.get());
	}
}
