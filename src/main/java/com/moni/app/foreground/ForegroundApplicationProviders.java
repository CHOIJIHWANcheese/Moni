package com.moni.app.foreground;

/** Factory for the supported foreground application provider on the current host. */
public final class ForegroundApplicationProviders {
    private ForegroundApplicationProviders() {
    }

    public static ForegroundApplicationProvider forCurrentPlatform() {
        return WindowsForegroundApplicationProvider.isSupported()
                ? new WindowsForegroundApplicationProvider()
                : OptionalForegroundApplicationProvider.NO_OP;
    }

    private enum OptionalForegroundApplicationProvider implements ForegroundApplicationProvider {
        NO_OP;

        @Override
        public java.util.Optional<ForegroundApplication> foregroundApplication() {
            return java.util.Optional.empty();
        }
    }
}
