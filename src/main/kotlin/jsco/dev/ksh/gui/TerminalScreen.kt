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

package jsco.dev.ksh.gui

import jsco.dev.ksh.Ksh
import jsco.dev.ksh.util.SharedVariables
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Style
import net.minecraft.text.Text
import java.awt.Color

class TerminalScreen : Screen(Text.of("ksh")) {

    private lateinit var textField: TextFieldWidget

    private val prefix: Text = Text.of("[").copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_BACKGROUND.rgb))
    private val brand: Text = Text.of("@ksh").copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_COLOR.rgb))
    private val suffix: Text = Text.of(" ~]$ ").copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_BACKGROUND.rgb))
    private lateinit var prompt: Text

    override fun init() {
        val username = Text.of(Ksh.client.player?.nameForScoreboard?.lowercase()).copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_COLOR.rgb))
        this.prompt = Text.empty()
            .append(prefix)
            .append(username)
            .append(brand)
            .append(suffix)

        this.textField = TextFieldWidget(Ksh.client.textRenderer, Ksh.client.textRenderer.getWidth(prompt) + 6, 8, this.width - 6, 14, Text.empty())
        this.textField.setDrawsBackground(false)
        this.textField.setFocusUnlocked(false)
        this.addSelectableChild(textField)
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        context!!.fill(2, 4, this.width - 2, this.height - 4, client!!.options.getTextBackgroundColor(Int.MIN_VALUE))
        context.drawBorder(2, 4, this.width - 4, this.height - 8, Color.WHITE.rgb)
        context.drawText(Ksh.client.textRenderer, prompt, 6, 8, Color.WHITE.rgb, false)
        this.textField.render(context, mouseX, mouseY, delta)
        super.render(context, mouseX, mouseY, delta);
    }

    override fun setInitialFocus() {
        this.setInitialFocus(this.textField)
    }

}