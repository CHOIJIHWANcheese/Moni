package com.moni.app.foreground;

import java.nio.file.Path;

/** A foreground Windows process resolved without collecting its window title. */
public record ForegroundApplication(long pid, String executableName, Path executablePath) {
}
