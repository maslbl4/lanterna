/*
 * This file is part of lanterna (http://code.google.com/p/lanterna/).
 * 
 * lanterna is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2010-2012 mabe02
 */

package com.googlecode.lanterna.terminal;

import com.googlecode.lanterna.LanternaException;
import java.io.*;

/**
 *
 * @author martin
 */
class ShellCommand
{
    static String exec(String ...cmd) throws LanternaException
    {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process process = pb.start();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream stdout = process.getInputStream();
            int readByte = stdout.read();
            while(readByte >= 0) {
                baos.write(readByte);
                readByte = stdout.read();
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
            StringBuilder builder = new StringBuilder();
            while(reader.ready()) {
                builder.append(reader.readLine());
            }
            reader.close();
            return builder.toString();
        }
        catch(IOException e) {
            throw new LanternaException(e);
        }
    }
}