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

package jsco.dev.ksh.command.impl

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import jsco.dev.ksh.Ksh
import jsco.dev.ksh.command.Command
import jsco.dev.ksh.command.CommandManager
import net.minecraft.command.CommandSource

class ShutdownCommand : Command("shutdown") {

    override fun build(builder: LiteralArgumentBuilder<CommandSource>) {
        builder.executes {
            Ksh.client.stop()
            singleSuccess()
        }
    }

}