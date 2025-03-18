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

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.CommandSource
import net.minecraft.text.MutableText
import net.minecraft.text.Text

abstract class Command(
    val name: String
) {

    abstract fun build(builder: LiteralArgumentBuilder<CommandSource>)

    companion object {
        @JvmStatic protected fun singleSuccess(): Int = 1

        fun info(message: String?) {
            CommandManager.log(Text.empty().append(message))
        }

        fun info (message: MutableText) {
            CommandManager.log(message)
        }
    }

    fun literal(literal: String?): LiteralArgumentBuilder<CommandSource> {
        return LiteralArgumentBuilder.literal(literal)
    }

    protected fun <T> argument(name: String?, argument: ArgumentType<T>?): RequiredArgumentBuilder<CommandSource, T> {
        return RequiredArgumentBuilder.argument(name, argument)
    }

}