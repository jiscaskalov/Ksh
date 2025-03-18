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
import jsco.dev.ksh.command.CommandManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import java.awt.Color
import kotlin.properties.Delegates

class TerminalScreen : Screen(Text.of("ksh")) {

    lateinit var textField: TextFieldWidget
    private lateinit var prompt: Text
    private var textBgColor by Delegates.notNull<Int>()

    var logs: MutableList<Pair<MutableText, Int>> = mutableListOf()
    private var currentLogY = 3
    private var inputY = 8

    override fun init() {
        this.prompt = CommandManager.getPrompt()
        val promptWidth = Ksh.client.textRenderer.getWidth(prompt)

        this.textBgColor = client?.options?.getTextBackgroundColor(Int.MIN_VALUE) ?: Color.BLACK.rgb
        this.textField = TextFieldWidget(Ksh.client.textRenderer, promptWidth + 6, inputY, this.width - 6, 14, Text.empty()).apply {
            setDrawsBackground(false)
            setFocusUnlocked(false)
        }
        this.addSelectableChild(textField)
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        context?.let {
            it.fill(2, 4, this.width - 2, this.height - 4, textBgColor)
            it.drawBorder(2, 4, this.width - 4, this.height - 8, Color.WHITE.rgb)
            it.drawText(Ksh.client.textRenderer, prompt, 6, inputY, Color.WHITE.rgb, true)
            logs.forEach { log ->
                it.drawText(Ksh.client.textRenderer, log.first, 6, log.second, Color.WHITE.rgb, true)
            }
            this.textField.render(it, mouseX, mouseY, delta)
        }
        super.render(context, mouseX, mouseY, delta);
    }

    override fun setInitialFocus() {
        super.setInitialFocus(this.textField)
    }

    fun log(msg: MutableText) {
        inputY += 8
        this.textField.y = inputY
        currentLogY = (inputY - 8) - 1
        logs.add(msg to currentLogY)
    }

    fun clearLogs() {
        logs.clear()
        inputY = 8
        this.textField.y = inputY
        currentLogY = 2
    }

}