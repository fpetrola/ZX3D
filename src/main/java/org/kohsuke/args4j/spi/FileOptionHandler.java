package org.kohsuke.args4j.spi;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;

import java.io.File;

/**
 * {@link File} {@link OptionHandler}.
 *
 * @author Kohsuke Kawaguchi
 */
public class FileOptionHandler extends OneArgumentOptionHandler<File> {
    public FileOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super File> setter) {
        super(parser, option, setter);
    }

    @Override
    protected File parse(String argument) throws CmdLineException {
        return new File(argument);
    }

    @Override
    public String getDefaultMetaVariable() {
        return "FILE";
    }
}
