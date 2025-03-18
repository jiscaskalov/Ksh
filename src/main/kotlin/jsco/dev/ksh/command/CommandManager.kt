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

package jsco.dev.ksh.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.ParseResults
import com.mojang.brigadier.exceptions.CommandSyntaxException
import jsco.dev.ksh.Ksh
import jsco.dev.ksh.gui.TerminalScreen
import jsco.dev.ksh.util.SharedVariables
import net.minecraft.client.network.ClientCommandSource
import net.minecraft.command.CommandSource
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.Text
import org.reflections.Reflections

object CommandManager {

    private val prefix: MutableText = Text.of("[").copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_BACKGROUND.rgb))
    private val brand: MutableText = Text.of("@ksh").copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_COLOR.rgb))
    private val suffix: MutableText = Text.of(" ~]$ ").copy().setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_BACKGROUND.rgb))

    val dispatcher: CommandDispatcher<CommandSource> = CommandDispatcher()
    val source: CommandSource = ClientCommandSource(null, Ksh.client)

    private fun register(command: Command) {
        val builder = command.literal(command.name)
        command.build(builder)
        dispatcher.register(builder)
    }

    fun registerCommands() {
        val reflections = Reflections("jsco.dev.ksh.command.impl")
        val classes = reflections.getSubTypesOf(Command::class.java)
        val commands = classes.mapNotNull { it.getDeclaredConstructor().newInstance() }
        commands.forEach { register(it) }

        Ksh.logger.info("Registered (${commands.size}) command(s)")
    }

    @Throws(CommandSyntaxException::class)
    fun dispatch(line: String?) {
        val results: ParseResults<CommandSource> = dispatcher.parse(line, ClientCommandSource(null, Ksh.client))
        dispatcher.execute(results)
    }

    fun log(msg: String) {
        log(Text.of(msg).copy())
    }

    fun log(msg: MutableText) {
        (Ksh.client.currentScreen as? TerminalScreen)?.apply {
            log(msg)
        }
    }

    fun getPrompt(): MutableText {
        val username = Text.of(Ksh.client.player?.nameForScoreboard?.lowercase()).copy()
            .setStyle(Style.EMPTY.withColor(SharedVariables.BRAND_COLOR.rgb))
        return prefix.copy().append(username).append(brand.copy()).append(suffix.copy())
    }

}