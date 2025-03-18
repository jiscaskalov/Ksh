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

package jsco.dev.ksh.util

import org.lwjgl.glfw.GLFW

object Input {

    private val buttons = BooleanArray(16)

    fun setButtonState(button: Int, pressed: Boolean) {
        if (button >= 0 && button < buttons.size) buttons[button] = pressed
    }

    enum class KeyAction {
        Press,
        Repeat,
        Release;
        companion object {
            fun get(action: Int): KeyAction {
                return when (action) {
                    GLFW.GLFW_PRESS -> Press
                    GLFW.GLFW_RELEASE -> Release
                    else -> Repeat
                }
            }
        }
    }

}