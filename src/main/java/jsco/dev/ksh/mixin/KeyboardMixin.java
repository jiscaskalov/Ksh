/*
 * This file is part of Ksh <https://github.com/jiscaskalov/Ksh/>
 * Copyright (C) 2025 jiscaskalov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package jsco.dev.ksh.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import jsco.dev.ksh.Ksh;
import jsco.dev.ksh.command.CommandManager;
import jsco.dev.ksh.gui.TerminalScreen;
import jsco.dev.ksh.util.Input;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        Input.KeyAction keyAction = Input.KeyAction.Companion.get(action);
        MinecraftClient client = Ksh.Companion.getClient();
        if (keyAction == Input.KeyAction.Press) {
            if (key == GLFW.GLFW_KEY_GRAVE_ACCENT && client.world != null && !(client.currentScreen instanceof TerminalScreen)) {
                client.setScreen(new TerminalScreen());
            }
            if ((key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) && client.currentScreen instanceof TerminalScreen screen) {
                try {
                    CommandManager.INSTANCE.dispatch(screen.textField.getText());
                } catch (CommandSyntaxException e) {
                    CommandManager.INSTANCE.log(Text.of(e.getMessage()).copy().withColor(Color.RED.getRGB()));
                }
                screen.textField.setText("");
            }
            if (key == GLFW.GLFW_KEY_DELETE && client.world != null && client.currentScreen instanceof TerminalScreen screen) {
                screen.clearLogs();
            }
        }
        if (keyAction == Input.KeyAction.Release) {
            if (key == GLFW.GLFW_KEY_GRAVE_ACCENT && client.world != null && !(client.currentScreen instanceof TerminalScreen)) {
                ci.cancel();
            }
        }
    }

}
