package net.mistert2525.headdictionary;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/**
 * @author misterT2525
 */
@SuppressWarnings("WeakerAccess")
public class VersionChecker {
    private static final Gson GSON = new Gson();
    private static final Pattern SPLITTER = Pattern.compile("-", Pattern.LITERAL);

    public static CompletableFuture<Result> check(@NonNull JavaPlugin plugin,
                                                  @NonNull String repo, @NonNull String devBranch) {
        CompletableFuture<Result> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                String[] versions = splitVersion(plugin);
                switch (getType(versions)) {
                    case SNAPSHOT:
                        future.complete(checkSnapshot(repo, devBranch, getHash(versions)));
                        break;
                    case RELEASE:
                        // TODO
                        break;
                    default:
                        future.complete(new Result(Type.CUSTOM, 0));
                }
            } catch (Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        }, plugin.getName() + " Version Checker");

        return future;
    }


    public enum Type {
        SNAPSHOT,
        RELEASE,
        CUSTOM
    }

    @Data
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Result {
        private final Type type;
        private final int behind;
    }


    private static Result checkSnapshot(@NonNull String repo, @NonNull String devBranch,
                                        @NonNull String hash) throws IOException {
        JsonObject json = fetchJson(String.format("https://api.github.com/repos/%s/compare/%s...%s",
            repo, encodeForURL(devBranch), encodeForURL(hash)));
        String status = json.getAsJsonPrimitive("status").getAsString();
        if (!status.equals("identical") && !status.equals("behind")) {
            return new Result(Type.CUSTOM, 0);
        }

        int behind = json.getAsJsonPrimitive("behind_by").getAsInt();
        if (json.getAsJsonPrimitive("ahead_by").getAsInt() > 0) {
            return new Result(Type.CUSTOM, behind);
        } else {
            return new Result(Type.SNAPSHOT, behind);
        }
    }


    private static String[] splitVersion(@NonNull JavaPlugin plugin) {
        return SPLITTER.split(plugin.getDescription().getVersion());
    }

    private static Type getType(@NonNull String[] versions) {
        if (versions.length == 3 && versions[1].equals("SNAPSHOT")) {// XX.YY.ZZ-SNAPSHOT-HASH
            return Type.SNAPSHOT;
        }
        if (versions.length == 2) {// XX.YY.ZZ-HASH
            return Type.RELEASE;
        }
        return Type.CUSTOM;
    }

    private static String getHash(@NonNull String[] versions) {
        return versions[versions.length - 1];
    }


    @SneakyThrows
    private static String encodeForURL(@NonNull String raw) {
        return URLEncoder.encode(raw, "UTF-8");
    }

    private static JsonObject fetchJson(@NonNull String url) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = ((HttpURLConnection) new URL(url).openConnection());
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");

            connection.connect();

            try (Reader reader = new InputStreamReader(connection.getInputStream(), Charsets.UTF_8)) {
                return GSON.fromJson(reader, JsonObject.class);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
