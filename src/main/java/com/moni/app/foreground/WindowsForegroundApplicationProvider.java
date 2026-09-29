package com.moni.app.foreground;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.IntByReference;

/** Windows-only foreground process lookup using User32 and ProcessHandle. */
public final class WindowsForegroundApplicationProvider implements ForegroundApplicationProvider {
    @Override
    public Optional<ForegroundApplication> foregroundApplication() {
        if (!isSupported()) {
            return Optional.empty();
        }

        try {
            HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
            if (foregroundWindow == null) {
                return Optional.empty();
            }
            IntByReference processIdReference = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(foregroundWindow, processIdReference);
            long processId = Integer.toUnsignedLong(processIdReference.getValue());
            if (shouldExcludeProcess(processId, ProcessHandle.current().pid())) {
                return Optional.empty();
            }
            return ProcessHandle.of(processId).flatMap(this::fromProcessHandle);
        } catch (RuntimeException | LinkageError ignored) {
            return Optional.empty();
        }
    }

    static boolean isSupported() {
        return System.getProperty("os.name", "").startsWith("Windows");
    }

    static boolean shouldExcludeProcess(long processId, long currentProcessId) {
        return processId <= 0 || processId == currentProcessId;
    }

    private Optional<ForegroundApplication> fromProcessHandle(ProcessHandle processHandle) {
        try {
            return processHandle.info().command().flatMap(command -> toApplication(processHandle.pid(), command));
        } catch (SecurityException ignored) {
            return Optional.empty();
        }
    }

    private Optional<ForegroundApplication> toApplication(long processId, String command) {
        try {
            Path executablePath = Path.of(command).toAbsolutePath();
            Path executableFile = executablePath.getFileName();
            if (executableFile == null) {
                return Optional.empty();
            }
            return Optional.of(new ForegroundApplication(processId, executableFile.toString(), executablePath));
        } catch (InvalidPathException | SecurityException ignored) {
            return Optional.empty();
        }
    }
}
