package net.mistert2525.headdictionary;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author misterT2525
 */
public class VersionChecker implements Runnable {

    public enum VersionType {
        SNAPSHOT,
        RELEASE
    }

    public interface VersionResult {
    }

    @Data
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Version implements VersionResult {
        private final VersionType type;
        private int behind = 0;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CustomVersion implements VersionResult {
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExceptionResult implements VersionResult {
        @Getter
        private final Throwable exception;
    }


    public interface Callback {
        void done(VersionResult result);
    }


    private static final Gson gson = new Gson();

    private final JavaPlugin plugin;
    private final String repositoryLocation;
    private final String devBranch;
    private final Callback callback;

    @Getter
    private VersionResult result;

    public VersionChecker(@NonNull JavaPlugin plugin, @NonNull String repositoryLocation, @NonNull String devBranch, Callback callback) {
        this.plugin = plugin;
        this.repositoryLocation = repositoryLocation;
        this.devBranch = devBranch;
        this.callback = callback;

        new Thread(this, plugin.getName() + " Version Checker").start();
    }

    @Override
    public void run() {
        try {
            String version = plugin.getDescription().getVersion();

            if (!version.contains("-")) {
                done(new CustomVersion());
                return;
            }
            String[] versions = version.split("-");

            // Snapshot
            if (versions.length == 3 && versions[1].equals("SNAPSHOT")) {
                String hash = versions[2];
                String url = String.format("https://api.github.com/repos/%s/compare/%s...%s", repositoryLocation, encode(devBranch), encode(hash));
                JsonObject json = http(url);

                String status = json.getAsJsonPrimitive("status").getAsString();
                if (!status.equals("identical") && !status.equals("behind")) {
                    done(new CustomVersion());
                    return;
                }
                if (json.getAsJsonPrimitive("ahead_by").getAsInt() > 0) {
                    done(new CustomVersion());
                    return;
                }

                int behind = json.getAsJsonPrimitive("behind_by").getAsInt();

                done(new Version(VersionType.SNAPSHOT, behind));
                return;
            }

            // Release
            if (versions.length == 2) {
                // TODO
                done(new Version(VersionType.RELEASE, 0));
                return;
            }

            done(new CustomVersion());
        } catch (Throwable e) {
            done(new ExceptionResult(e));
        }
    }

    private void done(VersionResult result) {
        if (this.result != null) {
            throw new IllegalStateException("Already completed");
        }

        this.result = result;

        if (callback != null) {
            Bukkit.getScheduler().runTask(plugin, () -> callback.done(result));
        }
    }

    @SneakyThrows
    private String encode(@NonNull String raw) {
        return URLEncoder.encode(raw, "UTF-8");
    }

    private JsonObject http(@NonNull String url) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = ((HttpURLConnection) new URL(url).openConnection());
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");

            connection.connect();

            try (Reader reader = new InputStreamReader(connection.getInputStream(), Charsets.UTF_8)) {
                return gson.fromJson(reader, JsonObject.class);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
