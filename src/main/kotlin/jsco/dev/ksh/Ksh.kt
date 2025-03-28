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

package jsco.dev.ksh

import jsco.dev.ksh.command.CommandManager
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Ksh : ClientModInitializer {

    companion object {
        const val NAME = "Ksh"
        const val VERSION = "0.0.1a"
        val logger: Logger = LoggerFactory.getLogger(NAME)
        val client = MinecraftClient.getInstance()
    }

    override fun onInitializeClient() {
        CommandManager.registerCommands()
    }

}
